package com.smartcity360.partC_chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract handler in the municipal escalation pipeline.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public abstract class IncidentHandler {
    protected IncidentHandler nextInChain;

    public IncidentHandler setSuccessor(IncidentHandler next) {
        this.nextInChain = next;
        return next;
    }

    public IncidentHandler getSuccessor() {
        return nextInChain;
    }

    public void handleIncident(IncidentTicket ticket) {
        processWithAuditTrail(ticket, new ArrayList<>());
    }

    public abstract TicketResolution processWithAuditTrail(IncidentTicket ticket, List<String> eventLog);
}
