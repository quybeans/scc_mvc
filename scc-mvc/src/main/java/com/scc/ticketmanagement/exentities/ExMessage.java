package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.MessageEntity;

/**
 * Created by Thien on 11/20/2016.
 */
public class ExMessage extends MessageEntity {
    private boolean ticket;

    public boolean isTicket() {
        return ticket;
    }

    public void setTicket(boolean ticket) {
        this.ticket = ticket;
    }
}