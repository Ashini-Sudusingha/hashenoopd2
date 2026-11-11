package com.smartcity360.partE_interpreter;

/**
 * Terminal expression verifying vehicle parking dwell duration.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class ParkingDurationRule implements CityRule {
    private final int maxStayHours;

    public ParkingDurationRule(int maxStayHours) {
        this.maxStayHours = maxStayHours;
    }

    @Override
    public boolean evaluate(UrbanRuleContext context) {
        if (!context.hasKey("parkedHours")) return false;
        int hoursParked = context.getIntValue("parkedHours");
        boolean compliant = hoursParked <= maxStayHours;
        System.out.println("  [DSL Interpreter] Parking Cap <= " + maxStayHours + " hrs vs Logged " + hoursParked + " hrs -> " + (compliant ? "PASSED" : "OVERSTAY VIOLATION"));
        return compliant;
    }
}
