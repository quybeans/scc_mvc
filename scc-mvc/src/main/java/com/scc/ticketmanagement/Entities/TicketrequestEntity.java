package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by user on 11/10/2016.
 */
@Entity
@Table(name = "ticketrequest", schema = "scc", catalog = "")
public class TicketrequestEntity {
    private int id;
    private Integer ticketid;
    private Integer assigner;
    private Integer assignee;
    private Timestamp requestat;
    private String note;

    @Id
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
    @Column(name = "assigner")
    public Integer getAssigner() {
        return assigner;
    }

    public void setAssigner(Integer assigner) {
        this.assigner = assigner;
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
    @Column(name = "requestat")
    public Timestamp getRequestat() {
        return requestat;
    }

    public void setRequestat(Timestamp requestat) {
        this.requestat = requestat;
    }

    @Basic
    @Column(name = "note")
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
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
