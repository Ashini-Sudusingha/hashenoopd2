package com.smartcity360.partC_chainofresponsibility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result model capturing resolution outcome and chronological escalation audit.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class TicketResolution {
    private final String ticketCode;
    private final String citizenName;
    private final String problemSummary;
    private final IncidentSeverity severity;
    private final String resolvedByOffice;
    private final boolean resolved;
    private final List<String> auditLog;

    public TicketResolution(String ticketCode, String citizenName, String problemSummary,
                            IncidentSeverity severity, String resolvedByOffice,
                            boolean resolved, List<String> auditLog) {
        this.ticketCode = ticketCode;
        this.citizenName = citizenName;
        this.problemSummary = problemSummary;
        this.severity = severity;
        this.resolvedByOffice = resolvedByOffice;
        this.resolved = resolved;
        this.auditLog = new ArrayList<>(auditLog);
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

    public String getResolvedByOffice() {
        return resolvedByOffice;
    }

    public boolean isResolved() {
        return resolved;
    }

    public List<String> getAuditLog() {
        return Collections.unmodifiableList(auditLog);
    }
}
