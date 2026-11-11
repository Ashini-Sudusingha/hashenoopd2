package com.smartcity360.partE_interpreter;

/**
 * Terminal expression evaluating time windows (including overnight spans).
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class HourSpanRule implements CityRule {
    private final int openingHour;
    private final int closingHour;

    public HourSpanRule(int openingHour, int closingHour) {
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    @Override
    public boolean evaluate(UrbanRuleContext context) {
        if (!context.hasKey("currentHour")) return false;
        int currentHour = context.getIntValue("currentHour");
        boolean withinWindow;
        if (openingHour <= closingHour) {
            withinWindow = (currentHour >= openingHour && currentHour <= closingHour);
        } else {
            withinWindow = (currentHour >= openingHour || currentHour <= closingHour);
        }
        System.out.println("  [DSL Interpreter] Window [" + openingHour + ":00 - " + closingHour + ":00] vs Current " + currentHour + ":00 -> " + withinWindow);
        return withinWindow;
    }
}
