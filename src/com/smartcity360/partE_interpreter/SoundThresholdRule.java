package com.smartcity360.partE_interpreter;

/**
 * Terminal expression verifying decibel sound limits.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class SoundThresholdRule implements CityRule {
    private final double maxDecibelsPermitted;

    public SoundThresholdRule(double maxDecibelsPermitted) {
        this.maxDecibelsPermitted = maxDecibelsPermitted;
    }

    @Override
    public boolean evaluate(UrbanRuleContext context) {
        if (!context.hasKey("noiseDecibels")) return false;
        double currentDecibels = context.getDoubleValue("noiseDecibels");
        boolean compliant = currentDecibels <= maxDecibelsPermitted;
        System.out.println("  [DSL Interpreter] Noise Limit <= " + maxDecibelsPermitted + " dB vs Measured " + currentDecibels + " dB -> " + (compliant ? "PASSED" : "VIOLATION"));
        return compliant;
    }
}
