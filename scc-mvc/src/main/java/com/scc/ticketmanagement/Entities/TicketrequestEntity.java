package com.scc.ticketmanagement.Entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Thien on 10/28/2016.
 */
@Entity
@javax.persistence.Table(name = "ticketrequest", schema = "scc", catalog = "")
public class TicketrequestEntity {
    private int id;

    @Id
    @javax.persistence.Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Integer ticketid;

    @Basic
    @javax.persistence.Column(name = "ticketid")
    public Integer getTicketid() {
        return ticketid;
    }

    public void setTicketid(Integer ticketid) {
        this.ticketid = ticketid;
    }

    private Integer assigner;

    @Basic
    @javax.persistence.Column(name = "assigner")
    public Integer getAssigner() {
        return assigner;
    }

    public void setAssigner(Integer assigner) {
        this.assigner = assigner;
    }

    private Integer assignee;

    @Basic
    @javax.persistence.Column(name = "assignee")
    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

    private Timestamp requestat;

    @Basic
    @javax.persistence.Column(name = "requestat")
    public Timestamp getRequestat() {
        return requestat;
    }

    public void setRequestat(Timestamp requestat) {
        this.requestat = requestat;
    }

    private Boolean status;

    @Basic
    @javax.persistence.Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private String note;

    @Basic
    @javax.persistence.Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketrequestEntity that = (TicketrequestEntity) o;

        if (id != that.id) return false;
        if (ticketid != null ? !ticketid.equals(that.ticketid) : that.ticketid != null) return false;
        if (assigner != null ? !assigner.equals(that.assigner) : that.assigner != null) return false;
        if (assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) return false;
        if (requestat != null ? !requestat.equals(that.requestat) : that.requestat != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ticketid != null ? ticketid.hashCode() : 0);
        result = 31 * result + (assigner != null ? assigner.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (requestat != null ? requestat.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
