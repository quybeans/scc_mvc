package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.CommentEntity;

/**
 * Created by user on 10/26/2016.
 */
public class ExtendComments extends CommentEntity {

    private boolean isticket;

    public boolean isticket() {
        return isticket;
    }

    public void setIsticket(boolean isticket) {
        this.isticket = isticket;
    }
}