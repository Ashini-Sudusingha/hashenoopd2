package com.smartcity360.partC_chainofresponsibility;

import java.util.List;

/**
 * Executive handler: Handles critical metropolitan emergencies.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class ChiefMunicipalOfficerHandler extends IncidentHandler {

    @Override
    public TicketResolution processWithAuditTrail(IncidentTicket ticket, List<String> eventLog) {
        eventLog.add("[Chief Municipal Officer] Executive emergency review for incident #" + ticket.getTicketCode());

        String resolutionNotice = "[Chief Municipal Officer RESOLVED] Executive Emergency Action Directive Issued: " + ticket.getProblemSummary();
        eventLog.add(resolutionNotice);
        System.out.println(resolutionNotice);

        return new TicketResolution(ticket.getTicketCode(), ticket.getCitizenName(),
                ticket.getProblemSummary(), ticket.getSeverity(), "Chief Municipal Officer", true, eventLog);
    }
}
