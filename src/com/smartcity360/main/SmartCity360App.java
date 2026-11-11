package com.smartcity360.main;

import com.smartcity360.partA_decorator.*;
import com.smartcity360.partB_composite.*;
import com.smartcity360.partC_chainofresponsibility.*;
import com.smartcity360.partD_flyweight.*;
import com.smartcity360.partE_interpreter.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Console Demonstration Entry Point for SmartCity360.
 * Verifies algorithmic correctness of all 5 GoF Design Patterns.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class SmartCity360App {

    public static void main(String[] args) {
        System.out.println("SmartCity360 - Urban Operations Platform");
        System.out.println("Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01\n");

        demoPartA();
        demoPartB();
        demoPartC();
        demoPartD();
        demoPartE();
    }

    // Part A: Decorator Pattern Demonstration
    private static void demoPartA() {
        System.out.println("--- Part A: Decorator Pattern (Data Stream Security) ---");

        String rawTelemetry = "{ meterId: 'EM-FORT-102', readingKw: 560.40, status: 'NORMAL' }";
        System.out.println("Raw Hardware Reading: " + rawTelemetry + "\n");

        // Assemble decorator pipeline
        TelemetryStream stream = new GridMeterStream();
        stream = new SignatureAuthDecorator(stream, "TOKEN_AUTH_HASHEN_772");
        stream = new AESEncryptionDecorator(stream);
        stream = new PayloadCompressionDecorator(stream);

        System.out.println("Transmitting telemetry across secured pipeline...");
        stream.transmit(rawTelemetry);

        System.out.println("\nReceiving and reversing security layers...");
        String decoded = stream.receive();
        System.out.println("Restored Payload: " + decoded + "\n");
    }

    // Part B: Composite Pattern Demonstration
    private static void demoPartB() {
        System.out.println("--- Part B: Composite Pattern (Urban Power Grid Hierarchy) ---");

        GridZone metropolitanCity = new GridZone("Greater Colombo Smart Grid", "City");

        GridZone fortDistrict = new GridZone("Colombo 01 - Fort Financial Sector", "District");
        GridZone yorkStreet = new GridZone("York Street Commercial Sector", "Street");
        yorkStreet.attach(new FacilityNode("World Trade Center Tower A", 1420.0));
        yorkStreet.attach(new FacilityNode("Central Bank Operations Hub", 850.5));

        GridZone chathamStreet = new GridZone("Chatham Street Heritage Zone", "Street");
        chathamStreet.attach(new FacilityNode("Kingsbury Maritime Complex", 510.0));

        fortDistrict.attach(yorkStreet);
        fortDistrict.attach(chathamStreet);

        GridZone bambaDistrict = new GridZone("Colombo 04 - Bambalapitiya IT Corridor", "District");
        GridZone dupRoad = new GridZone("Duplication Road Technology Block", "Street");
        dupRoad.attach(new FacilityNode("Unity Plaza Digital Hub", 920.0));
        dupRoad.attach(new FacilityNode("TechCorp Regional Campus", 1180.5));

        bambaDistrict.attach(dupRoad);

        metropolitanCity.attach(fortDistrict);
        metropolitanCity.attach(bambaDistrict);

        System.out.println("Displaying Spatial Grid Topology:");
        metropolitanCity.displayLayout("");
        System.out.println("Total Grid Demand: " + metropolitanCity.computePowerLoad() + " kW\n");

        System.out.println("Executing load-shedding cut on York Street Commercial Sector...");
        yorkStreet.cutPower();
        System.out.println("\nUpdated Metropolitan Power Demand: " + metropolitanCity.computePowerLoad() + " kW\n");
    }

    // Part C: Chain of Responsibility Demonstration
    private static void demoPartC() {
        System.out.println("--- Part C: Chain of Responsibility (Incident Escalation Pipeline) ---");

        IncidentHandler frontDesk = new FrontDeskHandler();
        IncidentHandler supervisor = new ZonalSupervisorHandler();
        IncidentHandler chief = new ChiefMunicipalOfficerHandler();

        frontDesk.setSuccessor(supervisor).setSuccessor(chief);

        IncidentTicket t1 = new IncidentTicket("TCK-501", "Dilan Abeysekara", "Recycling station bin damaged at Galle Face", IncidentSeverity.MINOR);
        IncidentTicket t2 = new IncidentTicket("TCK-502", "Sachini Jayasuriya", "Subsurface water transmission main ruptured on 3rd Cross Street", IncidentSeverity.URGENT);
        IncidentTicket t3 = new IncidentTicket("TCK-503", "Kanishka Perera", "Explosion in primary substation transformer near hospital", IncidentSeverity.CRITICAL);

        System.out.println("Processing Incident 1 (MINOR):");
        frontDesk.handleIncident(t1);

        System.out.println("\nProcessing Incident 2 (URGENT):");
        frontDesk.handleIncident(t2);

        System.out.println("\nProcessing Incident 3 (CRITICAL):");
        frontDesk.handleIncident(t3);
        System.out.println();
    }

    // Part D: Flyweight Pattern Demonstration
    private static void demoPartD() {
        System.out.println("--- Part D: Flyweight Pattern (Smart Streetlight Grid) ---");

        LuminaireType highMast = LuminaireFactory.getLuminaire("Cree XSP HighMast LED", 160, "5700K Daylight", "Cree Inc.");
        LuminaireType urbanSolar = LuminaireFactory.getLuminaire("Syska SolarCity LED", 50, "3500K WarmWhite", "Syska Systems");

        List<StreetLuminaire> streetLuminaires = new ArrayList<>();
        System.out.println("Deploying 10,000 street luminaires...");
        for (int i = 1; i <= 5000; i++) {
            streetLuminaires.add(new StreetLuminaire("LP-HW-" + i, 6.9320 + (i * 0.0001), 79.8450 + (i * 0.0001), highMast));
        }
        for (int i = 5001; i <= 10000; i++) {
            streetLuminaires.add(new StreetLuminaire("LP-RES-" + i, 6.9050 + (i * 0.0001), 79.8550 + (i * 0.0001), urbanSolar));
        }

        System.out.println("Total Luminaire Nodes Instantiated: " + streetLuminaires.size());
        System.out.println("Unique Intrinsic Specification Objects in Memory: " + LuminaireFactory.getCacheCount());

        System.out.println("\nTesting operational status on sample poles:");
        streetLuminaires.get(0).turnOn();
        streetLuminaires.get(0).displayStatus();

        streetLuminaires.get(7500).turnOn();
        streetLuminaires.get(7500).displayStatus();
        System.out.println();
    }

    // Part E: Interpreter Pattern Demonstration
    private static void demoPartE() {
        System.out.println("--- Part E: Interpreter Pattern (Municipal Policy DSL) ---");

        CityRule nightTime = new HourSpanRule(22, 6);
        CityRule soundLimit = new SoundThresholdRule(50.0);
        CityRule quietHoursPolicy = new ConjunctionRule(nightTime, soundLimit);

        CityRule dayWindow = new HourSpanRule(8, 18);
        CityRule parkingCap = new ParkingDurationRule(2);
        CityRule commercialParkingPolicy = new ConjunctionRule(dayWindow, parkingCap);

        CityRule combinedDistrictPolicy = new DisjunctionRule(quietHoursPolicy, commercialParkingPolicy);

        // Scenario 1: Night infraction
        UrbanRuleContext c1 = new UrbanRuleContext();
        c1.setContextParameter("currentHour", 23);
        c1.setContextParameter("noiseDecibels", 75.0);
        System.out.println("Evaluating Scenario 1: Commercial Club @ 23:00 (75 dB):");
        boolean v1 = quietHoursPolicy.evaluate(c1);
        System.out.println("Result: " + (v1 ? "COMPLIANT" : "VIOLATION"));

        // Scenario 2: Overstay parking
        UrbanRuleContext c2 = new UrbanRuleContext();
        c2.setContextParameter("currentHour", 15);
        c2.setContextParameter("parkedHours", 4);
        System.out.println("\nEvaluating Scenario 2: Vehicle parked 4 hours @ 15:00:");
        boolean v2 = commercialParkingPolicy.evaluate(c2);
        System.out.println("Result: " + (v2 ? "COMPLIANT" : "OVERSTAY VIOLATION"));

        // Scenario 3: Compliant residential night
        UrbanRuleContext c3 = new UrbanRuleContext();
        c3.setContextParameter("currentHour", 2);
        c3.setContextParameter("noiseDecibels", 35.0);
        System.out.println("\nEvaluating Scenario 3: Residential District @ 02:00 (35 dB):");
        boolean v3 = quietHoursPolicy.evaluate(c3);
        System.out.println("Result: " + (v3 ? "COMPLIANT" : "VIOLATION") + "\n");
    }
}
