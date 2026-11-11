package com.smartcity360.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smartcity360.controller.CityOperationsManager;
import com.smartcity360.partC_chainofresponsibility.IncidentSeverity;
import com.smartcity360.partC_chainofresponsibility.TicketResolution;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Embedded HTTP server engine for SmartCity360 web operations.
 * Listens on Port 8081.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class MetroWebServer {
    public static final int PORT = 8081;
    public static final String WEB_ROOT = "web";

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

            // API Endpoint Mappings
            server.createContext("/api/stream/security", new StreamSecurityHandler());
            server.createContext("/api/grid/status", new GridStatusHandler());
            server.createContext("/api/grid/power-toggle", new GridPowerToggleHandler());
            server.createContext("/api/incident/report", new IncidentReportHandler());
            server.createContext("/api/luminaire/deploy", new LuminaireDeployHandler());
            server.createContext("/api/policy/evaluate", new PolicyEvaluateHandler());

            // Static File Serving
            server.createContext("/", new StaticAssetHandler());

            server.setExecutor(Executors.newFixedThreadPool(16));
            server.start();

            System.out.println("SmartCity360 Web Operations Engine - Hashen Liyanaarachchi (JIAT/OODP II /EX/01)");
            System.out.println("Server running live at: http://localhost:" + PORT);
            System.out.println("Active MVC endpoints mapped across all 5 GoF patterns.\n");

        } catch (IOException e) {
            System.err.println("Fatal: Could not launch MetroWebServer on port " + PORT + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Static Asset Handler
    static class StaticAssetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path == null || path.equals("/") || path.isEmpty()) {
                path = "/index.html";
            }

            Path file = Paths.get(WEB_ROOT, path);
            if (!Files.exists(file) || Files.isDirectory(file)) {
                String notFound = "404 Not Found: " + path;
                exchange.sendResponseHeaders(404, notFound.length());
                OutputStream os = exchange.getResponseBody();
                os.write(notFound.getBytes());
                os.close();
                return;
            }

            String contentType = determineMime(path);
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, Files.size(file));

            OutputStream os = exchange.getResponseBody();
            Files.copy(file, os);
            os.close();
        }

        private String determineMime(String path) {
            if (path.endsWith(".html")) return "text/html; charset=UTF-8";
            if (path.endsWith(".css")) return "text/css; charset=UTF-8";
            if (path.endsWith(".js")) return "application/javascript; charset=UTF-8";
            if (path.endsWith(".json")) return "application/json; charset=UTF-8";
            if (path.endsWith(".svg")) return "image/svg+xml";
            if (path.endsWith(".png")) return "image/png";
            return "text/plain";
        }
    }

    // Part A Handler
    static class StreamSecurityHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configureHeaders(exchange);
            String body = readBody(exchange);

            String raw = "{ meterId: 'EM-FORT-102', readingKw: 560.40, status: 'NORMAL' }";
            boolean auth = true;
            String token = "TOKEN_AUTH_HASHEN_772";
            boolean enc = true;
            boolean comp = true;

            if (!body.isEmpty()) {
                try {
                    JsonObject req = JsonParser.parseString(body).getAsJsonObject();
                    if (req.has("payload") && !req.get("payload").getAsString().isEmpty()) raw = req.get("payload").getAsString();
                    if (req.has("enableAuth")) auth = req.get("enableAuth").getAsBoolean();
                    if (req.has("token")) token = req.get("token").getAsString();
                    if (req.has("enableEncrypt")) enc = req.get("enableEncrypt").getAsBoolean();
                    if (req.has("enableCompress")) comp = req.get("enableCompress").getAsBoolean();
                } catch (Exception ignored) {}
            }

            JsonObject resp = CityOperationsManager.getInstance().transmitSecurityStream(raw, auth, token, enc, comp);
            sendJson(exchange, resp.toString());
        }
    }

    // Part B Handlers
    static class GridStatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configureHeaders(exchange);
            JsonObject resp = CityOperationsManager.getInstance().getPowerGridStatus();
            sendJson(exchange, resp.toString());
        }
    }

    static class GridPowerToggleHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configureHeaders(exchange);
            String body = readBody(exchange);
            String target = "";
            boolean energize = false;

            if (!body.isEmpty()) {
                try {
                    JsonObject req = JsonParser.parseString(body).getAsJsonObject();
                    if (req.has("targetNode")) target = req.get("targetNode").getAsString();
                    if (req.has("energize")) energize = req.get("energize").getAsBoolean();
                } catch (Exception ignored) {}
            }

            JsonObject resp = CityOperationsManager.getInstance().toggleGridPower(target, energize);
            sendJson(exchange, resp.toString());
        }
    }

    // Part C Handler
    static class IncidentReportHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configureHeaders(exchange);
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = readBody(exchange);
                String citizen = "Citizen";
                String detail = "Civic Infrastructure Defect";
                IncidentSeverity sev = IncidentSeverity.MINOR;

                if (!body.isEmpty()) {
                    try {
                        JsonObject req = JsonParser.parseString(body).getAsJsonObject();
                        if (req.has("citizenName")) citizen = req.get("citizenName").getAsString();
                        if (req.has("incidentDetail")) detail = req.get("incidentDetail").getAsString();
                        if (req.has("severity")) {
                            sev = IncidentSeverity.valueOf(req.get("severity").getAsString().toUpperCase());
                        }
                    } catch (Exception ignored) {}
                }

                TicketResolution tr = CityOperationsManager.getInstance().submitIncidentReport(citizen, detail, sev);
                sendJson(exchange, CityOperationsManager.getInstance().getGson().toJson(tr));
            } else {
                List<TicketResolution> history = CityOperationsManager.getInstance().getTicketHistory();
                JsonObject res = new JsonObject();
                res.addProperty("status", "SUCCESS");
                res.add("history", CityOperationsManager.getInstance().getGson().toJsonTree(history));
                sendJson(exchange, res.toString());
            }
        }
    }

    // Part D Handler
    static class LuminaireDeployHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configureHeaders(exchange);
            int count = 10000;
            int highmast = 50;

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = readBody(exchange);
                if (!body.isEmpty()) {
                    try {
                        JsonObject req = JsonParser.parseString(body).getAsJsonObject();
                        if (req.has("count")) count = req.get("count").getAsInt();
                        if (req.has("highmastPercent")) highmast = req.get("highmastPercent").getAsInt();
                    } catch (Exception ignored) {}
                }
            }

            JsonObject resp = CityOperationsManager.getInstance().deployLuminaireBatch(count, highmast);
            sendJson(exchange, resp.toString());
        }
    }

    // Part E Handler
    static class PolicyEvaluateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configureHeaders(exchange);
            String mode = "QUIET_HOURS";
            int hour = 23;
            double noise = 75.0;
            int parked = 1;

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = readBody(exchange);
                if (!body.isEmpty()) {
                    try {
                        JsonObject req = JsonParser.parseString(body).getAsJsonObject();
                        if (req.has("policyMode")) mode = req.get("policyMode").getAsString();
                        if (req.has("currentHour")) hour = req.get("currentHour").getAsInt();
                        if (req.has("noiseDecibels")) noise = req.get("noiseDecibels").getAsDouble();
                        if (req.has("parkedHours")) parked = req.get("parkedHours").getAsInt();
                    } catch (Exception ignored) {}
                }
            }

            JsonObject resp = CityOperationsManager.getInstance().interpretMunicipalRegulation(mode, hour, noise, parked);
            sendJson(exchange, resp.toString());
        }
    }

    // Utility Methods
    private static void configureHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static String readBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static void sendJson(HttpExchange exchange, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
