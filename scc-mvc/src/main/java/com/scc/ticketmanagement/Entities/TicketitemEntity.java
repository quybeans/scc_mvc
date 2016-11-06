package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by user on 11/6/2016.
 */
@Entity
@Table(name = "ticketitem", schema = "scc", catalog = "")
@IdClass(TicketitemEntityPK.class)
public class TicketitemEntity {
    private int ticketid;
    private String commentid;
    private String postid;
    private String messageid;

    @Id
    @Column(name = "ticketid")
    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    @Id
    @Column(name = "commentid")
    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    @Id
    @Column(name = "postid")
    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    @Id
    @Column(name = "messageid")
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

        TicketitemEntity that = (TicketitemEntity) o;

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
