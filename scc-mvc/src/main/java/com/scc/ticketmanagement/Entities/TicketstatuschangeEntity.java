package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by user on 11/8/2016.
 */
@Entity
@Table(name = "ticketstatuschange", schema = "scc", catalog = "")
public class TicketstatuschangeEntity {
    private int id;
    private Integer ticketid;
    private Integer changeby;
    private Integer statusid;
    private Timestamp createdat;
    private Integer assignee;
    private String note;
    private Integer priorityid;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ticketid")
    public Integer getTicketid() {
        return ticketid;
    }

    public void setTicketid(Integer ticketid) {
        this.ticketid = ticketid;
    }

    @Basic
    @Column(name = "changeby")
    public Integer getChangeby() {
        return changeby;
    }

    public void setChangeby(Integer changeby) {
        this.changeby = changeby;
    }

    @Basic
    @Column(name = "statusid")
    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    @Basic
    @Column(name = "createdat")
    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    @Basic
    @Column(name = "assignee")
    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "priorityid")
    public Integer getPriorityid() {
        return priorityid;
    }

    public void setPriorityid(Integer priorityid) {
        this.priorityid = priorityid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketstatuschangeEntity that = (TicketstatuschangeEntity) o;

        if (id != that.id) return false;
        if (ticketid != null ? !ticketid.equals(that.ticketid) : that.ticketid != null) return false;
        if (changeby != null ? !changeby.equals(that.changeby) : that.changeby != null) return false;
        if (statusid != null ? !statusid.equals(that.statusid) : that.statusid != null) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;
        if (assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (priorityid != null ? !priorityid.equals(that.priorityid) : that.priorityid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ticketid != null ? ticketid.hashCode() : 0);
        result = 31 * result + (changeby != null ? changeby.hashCode() : 0);
        result = 31 * result + (statusid != null ? statusid.hashCode() : 0);
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (priorityid != null ? priorityid.hashCode() : 0);
        return result;
    }
}
