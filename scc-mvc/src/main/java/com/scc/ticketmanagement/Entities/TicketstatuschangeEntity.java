package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
@Entity
@Table(name = "ticketstatuschange", schema = "scc", catalog = "")
@IdClass(TicketstatuschangeEntityPK.class)
public class TicketstatuschangeEntity {
    private int ticketid;
    private int userid;
    private int statusid;
    private Timestamp createdat;

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
    @Column(name = "statusid")
    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    @Id
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

        TicketstatuschangeEntity that = (TicketstatuschangeEntity) o;

        if (ticketid != that.ticketid) return false;
        if (userid != that.userid) return false;
        if (statusid != that.statusid) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketid;
        result = 31 * result + userid;
        result = 31 * result + statusid;
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        return result;
    }
}
