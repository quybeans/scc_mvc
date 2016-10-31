package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.AttributeEntity;

/**
 * Created by user on 10/31/2016.
 */
public class ExAttribute extends AttributeEntity{
    private boolean commentatt;

    public boolean isCommentatt() {
        return commentatt;
    }

    public void setCommentatt(boolean commentatt) {
        this.commentatt = commentatt;
    }
}
