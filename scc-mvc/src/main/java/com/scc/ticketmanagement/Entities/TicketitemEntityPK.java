package com.scc.ticketmanagement.Entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by user on 11/2/2016.
 */
public class TicketitemEntityPK implements Serializable {
    private int ticketid;
    private String commentid;
    private String postid;
    private String messageid;

    @Column(name = "ticketid")
    @Id
    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    @Column(name = "commentid")
    @Id
    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    @Column(name = "postid")
    @Id
    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    @Column(name = "messageid")
    @Id
    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketitemEntityPK that = (TicketitemEntityPK) o;

        if (ticketid != that.ticketid) return false;
        if (commentid != null ? !commentid.equals(that.commentid) : that.commentid != null) return false;
        if (postid != null ? !postid.equals(that.postid) : that.postid != null) return false;
        if (messageid != null ? !messageid.equals(that.messageid) : that.messageid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketid;
        result = 31 * result + (commentid != null ? commentid.hashCode() : 0);
        result = 31 * result + (postid != null ? postid.hashCode() : 0);
        result = 31 * result + (messageid != null ? messageid.hashCode() : 0);
        return result;
    }
}
