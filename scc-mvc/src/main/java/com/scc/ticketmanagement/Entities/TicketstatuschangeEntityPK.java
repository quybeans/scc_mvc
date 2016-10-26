package com.scc.ticketmanagement.Entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
public class TicketstatuschangeEntityPK implements Serializable {
    private int ticketid;
    private int userid;
    private Timestamp createdat;

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

    @Column(name = "createdat")
    @Id
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

        TicketstatuschangeEntityPK that = (TicketstatuschangeEntityPK) o;

        if (ticketid != that.ticketid) return false;
        if (userid != that.userid) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketid;
        result = 31 * result + userid;
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        return result;
    }
}
