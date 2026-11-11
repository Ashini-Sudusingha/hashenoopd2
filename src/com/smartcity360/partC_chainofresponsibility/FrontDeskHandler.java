package com.smartcity360.partC_chainofresponsibility;

import java.util.List;

/**
 * First-tier handler: Resolves minor civic infractions.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class FrontDeskHandler extends IncidentHandler {

    @Override
    public TicketResolution processWithAuditTrail(IncidentTicket ticket, List<String> eventLog) {
        eventLog.add("[FrontDesk Officer] Received ticket #" + ticket.getTicketCode() + " (" + ticket.getSeverity().getLabel() + ")");

        if (ticket.getSeverity() == IncidentSeverity.MINOR) {
            String resolutionNotice = "[FrontDesk Officer RESOLVED] Resolved by Ward Maintenance: " + ticket.getProblemSummary();
            eventLog.add(resolutionNotice);
            System.out.println(resolutionNotice);
            return new TicketResolution(ticket.getTicketCode(), ticket.getCitizenName(),
                    ticket.getProblemSummary(), ticket.getSeverity(), "Front Desk Support", true, eventLog);
        } else if (nextInChain != null) {
            String escalationNotice = "[FrontDesk Officer ESCALATED] Exceeds desk clearance. Forwarding to Zonal Supervisor...";
            eventLog.add(escalationNotice);
            System.out.println(escalationNotice);
            return nextInChain.processWithAuditTrail(ticket, eventLog);
        } else {
            return new TicketResolution(ticket.getTicketCode(), ticket.getCitizenName(),
                    ticket.getProblemSummary(), ticket.getSeverity(), "Unresolved", false, eventLog);
        }
    }
}
