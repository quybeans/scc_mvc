package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
@Entity
@Table(name = "ticketconversation", schema = "scc", catalog = "")
@IdClass(TicketconversationEntityPK.class)
public class TicketconversationEntity {
    private int commentid;
    private int ticketid;
    private int userid;
    private Timestamp createdat;

    @Id
    @Column(name = "commentid")
    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    @Id
    @Column(name = "ticketid")
    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    @Id
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "createdat")
    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketconversationEntity that = (TicketconversationEntity) o;

        if (commentid != that.commentid) return false;
        if (ticketid != that.ticketid) return false;
        if (userid != that.userid) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentid;
        result = 31 * result + ticketid;
        result = 31 * result + userid;
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        return result;
    }
}
