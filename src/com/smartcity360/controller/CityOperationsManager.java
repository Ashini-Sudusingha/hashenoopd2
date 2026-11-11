package com.smartcity360.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smartcity360.partA_decorator.*;
import com.smartcity360.partB_composite.*;
import com.smartcity360.partC_chainofresponsibility.*;
import com.smartcity360.partD_flyweight.*;
import com.smartcity360.partE_interpreter.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Centralized singleton facade coordinating domain model execution.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class CityOperationsManager {
    private static CityOperationsManager instance;
    private final Gson gson = new Gson();

    // Part B Grid Root
    private PowerGridComponent metropolitanRoot;

    // Part C Escalation Pipeline
    private IncidentHandler escalationChain;
    private final List<TicketResolution> ticketHistory = new ArrayList<>();
    private int ticketSequence = 501;

    // Part D Streetlights
    private final List<StreetLuminaire> activeLuminaires = new ArrayList<>();

    private CityOperationsManager() {
        initUrbanPowerGrid();
        initComplaintPipeline();
        initDefaultLuminaires();
    }

    public static synchronized CityOperationsManager getInstance() {
        if (instance == null) {
            instance = new CityOperationsManager();
        }
        return instance;
    }

    // Part A: Decorator Pattern Coordination
    public JsonObject transmitSecurityStream(String rawData, boolean enableAuth, String tokenKey,
                                             boolean enableEncrypt, boolean enableCompress) {
        JsonObject result = new JsonObject();
        JsonArray pipelineStages = new JsonArray();

        GridMeterStream baseStream = new GridMeterStream();
        TelemetryStream activeStream = baseStream;
        pipelineStages.add("Base Hardware Buffer instantiated: GridMeterStream");

        if (enableAuth) {
            String token = (tokenKey != null && !tokenKey.trim().isEmpty()) ? tokenKey.trim() : "TOKEN_AUTH_HASHEN_772";
            activeStream = new SignatureAuthDecorator(activeStream, token);
            pipelineStages.add("Layer Wrapped: SignatureAuthDecorator [Token: " + token + "]");
        }

        if (enableEncrypt) {
            activeStream = new AESEncryptionDecorator(activeStream);
            pipelineStages.add("Layer Wrapped: AESEncryptionDecorator (AES-256 / Base64 Cipher)");
        }

        if (enableCompress) {
            activeStream = new PayloadCompressionDecorator(activeStream);
            pipelineStages.add("Layer Wrapped: PayloadCompressionDecorator (GZIP Byte Compression)");
        }

        activeStream.transmit(rawData);
        String transmittedPayload = baseStream.receive();
        String receivedPlain = activeStream.receive();

        result.addProperty("status", "SUCCESS");
        result.addProperty("originalData", rawData);
        result.addProperty("transmittedWirePayload", transmittedPayload);
        result.addProperty("decipheredPayload", receivedPlain);
        result.addProperty("rawSize", rawData.getBytes().length);
        result.addProperty("wireSize", transmittedPayload.getBytes().length);
        result.add("pipelineStages", pipelineStages);

        return result;
    }

    // Part B: Composite Pattern Coordination
    private void initUrbanPowerGrid() {
        GridZone cityRoot = new GridZone("Greater Colombo Smart Grid", "City");

        // District 1: Colombo 01 Fort Financial District
        GridZone fortDistrict = new GridZone("Colombo 01 - Fort Financial Sector", "District");
        GridZone yorkStreet = new GridZone("York Street Commercial Sector", "Street");
        yorkStreet.attach(new FacilityNode("World Trade Center Tower A", 1420.0));
        yorkStreet.attach(new FacilityNode("Central Bank Operations Hub", 850.5));

        GridZone chathamStreet = new GridZone("Chatham Street Heritage Zone", "Street");
        chathamStreet.attach(new FacilityNode("Kingsbury Maritime Complex", 510.0));

        fortDistrict.attach(yorkStreet);
        fortDistrict.attach(chathamStreet);

        // District 2: Colombo 04 Bambalapitiya IT Corridor
        GridZone bambaDistrict = new GridZone("Colombo 04 - Bambalapitiya IT Corridor", "District");
        GridZone dupRoad = new GridZone("Duplication Road Technology Block", "Street");
        dupRoad.attach(new FacilityNode("Unity Plaza Digital Hub", 920.0));
        dupRoad.attach(new FacilityNode("TechCorp Regional Campus", 1180.5));

        bambaDistrict.attach(dupRoad);

        cityRoot.attach(fortDistrict);
        cityRoot.attach(bambaDistrict);

        this.metropolitanRoot = cityRoot;
    }

    public JsonObject getPowerGridStatus() {
        JsonObject response = new JsonObject();
        response.addProperty("status", "SUCCESS");
        response.addProperty("totalGridDemandKw", metropolitanRoot.computePowerLoad());
        response.addProperty("isGridEnergized", metropolitanRoot.isEnergized());
        response.add("gridHierarchy", serializeGridNode(metropolitanRoot));
        return response;
    }

    public JsonObject toggleGridPower(String targetName, boolean energize) {
        boolean executed = findAndToggleNode(metropolitanRoot, targetName, energize);
        JsonObject res = getPowerGridStatus();
        res.addProperty("commandExecuted", executed);
        res.addProperty("targetNode", targetName);
        res.addProperty("newState", energize ? "ENERGIZED" : "DE-ENERGIZED");
        return res;
    }

    private boolean findAndToggleNode(PowerGridComponent node, String name, boolean energize) {
        if (node.getTitle().equalsIgnoreCase(name)) {
            if (energize) node.energizePower();
            else node.cutPower();
            return true;
        }
        if (node instanceof GridZone) {
            for (PowerGridComponent child : ((GridZone) node).getSubNodes()) {
                if (findAndToggleNode(child, name, energize)) return true;
            }
        }
        return false;
    }

    private JsonObject serializeGridNode(PowerGridComponent component) {
        JsonObject obj = new JsonObject();
        obj.addProperty("title", component.getTitle());
        obj.addProperty("demandKw", component.computePowerLoad());
        obj.addProperty("energized", component.isEnergized());

        if (component instanceof GridZone) {
            GridZone zone = (GridZone) component;
            obj.addProperty("category", zone.getZoneCategory());
            obj.addProperty("isComposite", true);
            JsonArray children = new JsonArray();
            for (PowerGridComponent child : zone.getSubNodes()) {
                children.add(serializeGridNode(child));
            }
            obj.add("children", children);
        } else if (component instanceof FacilityNode) {
            FacilityNode fn = (FacilityNode) component;
            obj.addProperty("category", "Facility");
            obj.addProperty("isComposite", false);
            obj.addProperty("baseDemandKw", fn.getBasePowerDemandKw());
        }
        return obj;
    }

    // Part C: Chain of Responsibility Coordination
    private void initComplaintPipeline() {
        IncidentHandler desk = new FrontDeskHandler();
        IncidentHandler supervisor = new ZonalSupervisorHandler();
        IncidentHandler chief = new ChiefMunicipalOfficerHandler();

        desk.setSuccessor(supervisor).setSuccessor(chief);
        this.escalationChain = desk;

        // Seed initial sample tickets
        submitIncidentReport("Dilan Abeysekara", "Overflowing recycling station at Galle Face promenade", IncidentSeverity.MINOR);
        submitIncidentReport("Sachini Jayasuriya", "Subsurface water transmission main fractured on 3rd Cross Street", IncidentSeverity.URGENT);
        submitIncidentReport("Kanishka Perera", "Explosion in main power substation transformer near Colombo General Hospital", IncidentSeverity.CRITICAL);
    }

    public TicketResolution submitIncidentReport(String citizen, String detail, IncidentSeverity severity) {
        String code = "TCK-HSH-" + (ticketSequence++);
        IncidentTicket ticket = new IncidentTicket(code, citizen, detail, severity);
        List<String> log = new ArrayList<>();
        log.add("Citizen [" + citizen + "] filed report #" + code + " at " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        TicketResolution res = escalationChain.processWithAuditTrail(ticket, log);
        ticketHistory.add(0, res);
        return res;
    }

    public List<TicketResolution> getTicketHistory() {
        return Collections.unmodifiableList(ticketHistory);
    }

    // Part D: Flyweight Coordination
    private void initDefaultLuminaires() {
        deployLuminaireBatch(10000, 50);
    }

    public JsonObject deployLuminaireBatch(int totalCount, int highmastPercent) {
        activeLuminaires.clear();

        LuminaireType highMast = LuminaireFactory.getLuminaire("Cree XSP HighMast LED", 160, "5700K Daylight", "Cree Inc.");
        LuminaireType urbanSolar = LuminaireFactory.getLuminaire("Syska SolarCity LED", 50, "3500K WarmWhite", "Syska Systems");

        int highmastCount = (totalCount * highmastPercent) / 100;
        int solarCount = totalCount - highmastCount;

        for (int i = 1; i <= highmastCount; i++) {
            StreetLuminaire l = new StreetLuminaire("LP-HW-" + i, 6.9320 + (i * 0.00005), 79.8450 + (i * 0.00005), highMast);
            l.turnOn();
            activeLuminaires.add(l);
        }

        for (int i = 1; i <= solarCount; i++) {
            StreetLuminaire l = new StreetLuminaire("LP-RES-" + i, 6.9050 + (i * 0.00005), 79.8550 + (i * 0.00005), urbanSolar);
            if (i % 2 == 0) l.turnOn();
            activeLuminaires.add(l);
        }

        long naiveMemoryBytes = (long) totalCount * 128;
        long flyweightMemoryBytes = ((long) totalCount * 32) + (LuminaireFactory.getCacheCount() * 128);
        double ramSavedPercent = ((double) (naiveMemoryBytes - flyweightMemoryBytes) / naiveMemoryBytes) * 100.0;

        JsonObject res = new JsonObject();
        res.addProperty("status", "SUCCESS");
        res.addProperty("totalNodes", activeLuminaires.size());
        res.addProperty("flyweightPoolCount", LuminaireFactory.getCacheCount());
        res.addProperty("bytesWithoutFlyweight", naiveMemoryBytes);
        res.addProperty("bytesWithFlyweight", flyweightMemoryBytes);
        res.addProperty("savingsPercent", Math.round(ramSavedPercent * 10.0) / 10.0);

        JsonArray sampleArray = new JsonArray();
        int sampleCount = Math.min(activeLuminaires.size(), 12);
        for (int i = 0; i < sampleCount; i++) {
            StreetLuminaire sl = activeLuminaires.get(i);
            JsonObject item = new JsonObject();
            item.addProperty("poleNumber", sl.getPoleNumber());
            item.addProperty("latitude", sl.getGpsLatitude());
            item.addProperty("longitude", sl.getGpsLongitude());
            item.addProperty("illuminated", sl.isIlluminated());
            item.addProperty("model", sl.getSpecification().getModelCode());
            item.addProperty("wattage", sl.getSpecification().getWattageWatts());
            sampleArray.add(item);
        }
        res.add("sampleLuminaires", sampleArray);

        return res;
    }

    // Part E: Interpreter Coordination
    public JsonObject interpretMunicipalRegulation(String policyMode, int currentHour, double noiseDecibels, int parkedHours) {
        UrbanRuleContext context = new UrbanRuleContext();
        context.setContextParameter("currentHour", currentHour);
        context.setContextParameter("noiseDecibels", noiseDecibels);
        context.setContextParameter("parkedHours", parkedHours);

        CityRule ruleEngine;
        String ruleTitle;
        String astRepresentation;
        String fineAmount;

        if ("PARKING".equalsIgnoreCase(policyMode)) {
            ruleTitle = "Commercial Zone Daytime Parking Policy (08:00 - 18:00, <= 2 Hours)";
            astRepresentation = "ConjunctionRule(HourSpanRule(8, 18), ParkingDurationRule(2))";
            fineAmount = "LKR 2,500 Municipal Overstay Surcharge";
            ruleEngine = new ConjunctionRule(new HourSpanRule(8, 18), new ParkingDurationRule(2));
        } else if ("COMBINED".equalsIgnoreCase(policyMode)) {
            ruleTitle = "Metropolitan Smart District Combined Bylaw (Night Quiet Hours OR Day Parking)";
            astRepresentation = "DisjunctionRule(ConjunctionRule(HourSpan(22,6), Sound(50)), ConjunctionRule(HourSpan(8,18), Parking(2)))";
            fineAmount = "LKR 10,000 Special Municipal Summons";
            CityRule quietBranch = new ConjunctionRule(new HourSpanRule(22, 6), new SoundThresholdRule(50.0));
            CityRule parkingBranch = new ConjunctionRule(new HourSpanRule(8, 18), new ParkingDurationRule(2));
            ruleEngine = new DisjunctionRule(quietBranch, parkingBranch);
        } else {
            // Default Quiet Hours
            ruleTitle = "Metropolitan Night Quiet Hours Policy (22:00 - 06:00, <= 50 dB)";
            astRepresentation = "ConjunctionRule(HourSpanRule(22, 6), SoundThresholdRule(50.0))";
            fineAmount = "LKR 5,000 Urban Noise Pollution Fine";
            ruleEngine = new ConjunctionRule(new HourSpanRule(22, 6), new SoundThresholdRule(50.0));
        }

        boolean verdict = ruleEngine.evaluate(context);

        JsonObject response = new JsonObject();
        response.addProperty("status", "SUCCESS");
        response.addProperty("policyMode", policyMode);
        response.addProperty("ruleTitle", ruleTitle);
        response.addProperty("astRepresentation", astRepresentation);
        response.addProperty("currentHour", currentHour);
        response.addProperty("noiseDecibels", noiseDecibels);
        response.addProperty("parkedHours", parkedHours);
        response.addProperty("compliant", verdict);
        response.addProperty("verdictLabel", verdict ? "PASSED (Compliant with Urban Bylaws)" : "FAILED (Bylaw Infraction Detected)");
        response.addProperty("enforcementAction", verdict ? "Authorized municipal operation. No citation." : "ENFORCE: " + fineAmount);

        return response;
    }

    public Gson getGson() {
        return gson;
    }
}
