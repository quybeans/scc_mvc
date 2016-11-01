package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.CommentEntity;

/**
 * Created by user on 10/26/2016.
 */
public class ExtendComments extends CommentEntity {

    private boolean isticket;
    private boolean staff;
    private boolean ticketofstaff;

    public boolean isticket() {
        return isticket;
    }

    public void setIsticket(boolean isticket) {
        this.isticket = isticket;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public boolean isTicketofstaff() {
        return ticketofstaff;
    }

    public void setTicketofstaff(boolean ticketofstaff) {
        this.ticketofstaff = ticketofstaff;
    }
}
