package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.TicketrequestEntity;

/**
 * Created by user on 11/10/2016.
 */
public class ExtendTicketRequest extends TicketrequestEntity {
    private String assignername;
    private String ticketname;

    public String getAssignername() {
        return assignername;
    }

    public void setAssignername(String assignername) {
        this.assignername = assignername;
    }

    public String getTicketname() {
        return ticketname;
    }

    public void setTicketname(String ticketname) {
        this.ticketname = ticketname;
    }
}
