package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.CommentEntity;

import java.sql.Timestamp;

/**
 * Created by user on 11/7/2016.
 */
public class TicketItemAndHistory extends CommentEntity {
    private int ticketid;
    private String changeby;
    private int statusid;
    private String assignee;
    private String note;
    private boolean item;
    private Timestamp commentat;

    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getChangeby() {
        return changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public Timestamp getCommentat() {
        return commentat;
    }

    public void setCommentat(Timestamp commentat) {
        this.commentat = commentat;
    }
}
