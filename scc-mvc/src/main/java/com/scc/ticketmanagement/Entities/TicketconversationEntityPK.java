package com.scc.ticketmanagement.Entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
public class TicketconversationEntityPK implements Serializable {
    private int commentid;
    private int ticketid;
    private int userid;

    @Column(name = "commentid")
    @Id
    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    @Column(name = "ticketid")
    @Id
    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    @Column(name = "userid")
    @Id
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketconversationEntityPK that = (TicketconversationEntityPK) o;

        if (commentid != that.commentid) return false;
        if (ticketid != that.ticketid) return false;
        if (userid != that.userid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentid;
        result = 31 * result + ticketid;
        result = 31 * result + userid;
        return result;
    }
}
