package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by user on 11/8/2016.
 */
@Entity
@Table(name = "ticket", schema = "scc", catalog = "")
public class TicketEntity {
    private int id;
    private String name;
    private Integer createdby;
    private Timestamp createdtime;
    private Integer statusid;
    private Boolean active;
    private Integer assignee;
    private String note;
    private Integer priority;
    private Timestamp duetime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "createdby")
    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    @Basic
    @Column(name = "createdtime")
    public Timestamp getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Timestamp createdtime) {
        this.createdtime = createdtime;
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
    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "duetime")
    public Timestamp getDuetime() {
        return duetime;
    }

    public void setDuetime(Timestamp duetime) {
        this.duetime = duetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createdby != null ? !createdby.equals(that.createdby) : that.createdby != null) return false;
        if (createdtime != null ? !createdtime.equals(that.createdtime) : that.createdtime != null) return false;
        if (statusid != null ? !statusid.equals(that.statusid) : that.statusid != null) return false;
        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (priority != null ? !priority.equals(that.priority) : that.priority != null) return false;
        if (duetime != null ? !duetime.equals(that.duetime) : that.duetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdby != null ? createdby.hashCode() : 0);
        result = 31 * result + (createdtime != null ? createdtime.hashCode() : 0);
        result = 31 * result + (statusid != null ? statusid.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (duetime != null ? duetime.hashCode() : 0);
        return result;
    }
}
