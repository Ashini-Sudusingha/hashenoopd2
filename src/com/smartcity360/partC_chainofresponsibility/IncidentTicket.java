package com.smartcity360.partC_chainofresponsibility;

/**
 * Domain entity representing a citizen incident grievance report.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class IncidentTicket {
    private final String ticketCode;
    private final String citizenName;
    private final String problemSummary;
    private final IncidentSeverity severity;

    public IncidentTicket(String ticketCode, String citizenName, String problemSummary, IncidentSeverity severity) {
        this.ticketCode = ticketCode;
        this.citizenName = citizenName;
        this.problemSummary = problemSummary;
        this.severity = severity;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public String getProblemSummary() {
        return problemSummary;
    }

    public IncidentSeverity getSeverity() {
        return severity;
    }
}
