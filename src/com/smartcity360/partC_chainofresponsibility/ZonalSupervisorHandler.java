package com.smartcity360.partC_chainofresponsibility;

import java.util.List;

/**
 * Mid-tier handler: Resolves urgent public hazards via field response units.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class ZonalSupervisorHandler extends IncidentHandler {

    @Override
    public TicketResolution processWithAuditTrail(IncidentTicket ticket, List<String> eventLog) {
        eventLog.add("[Zonal Supervisor] Reviewing escalated incident #" + ticket.getTicketCode());

        if (ticket.getSeverity() == IncidentSeverity.URGENT) {
            String resolutionNotice = "[Zonal Supervisor RESOLVED] Dispatched Municipal Field Unit: " + ticket.getProblemSummary();
            eventLog.add(resolutionNotice);
            System.out.println(resolutionNotice);
            return new TicketResolution(ticket.getTicketCode(), ticket.getCitizenName(),
                    ticket.getProblemSummary(), ticket.getSeverity(), "Zonal Field Supervisor", true, eventLog);
        } else if (nextInChain != null) {
            String escalationNotice = "[Zonal Supervisor ESCALATED] Life-safety crisis detected. Escalating to Chief Municipal Officer...";
            eventLog.add(escalationNotice);
            System.out.println(escalationNotice);
            return nextInChain.processWithAuditTrail(ticket, eventLog);
        } else {
            return new TicketResolution(ticket.getTicketCode(), ticket.getCitizenName(),
                    ticket.getProblemSummary(), ticket.getSeverity(), "Unresolved", false, eventLog);
        }
    }
}
