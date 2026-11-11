package com.smartcity360.partC_chainofresponsibility;

/**
 * Severity grading for municipal citizen incident tickets.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public enum IncidentSeverity {
    MINOR("Minor Civic Infraction (Public cleanliness, minor signs)"),
    URGENT("Urgent Public Hazard (Water main rupture, broken traffic signals)"),
    CRITICAL("Critical Metropolitan Disaster (Electrical substation fire, gas leaks)");

    private final String label;

    IncidentSeverity(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
