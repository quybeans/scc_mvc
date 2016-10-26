package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
@Entity
@Table(name = "ticket", schema = "scc", catalog = "")
public class TicketEntity {
    private int id;
    private int commentid;
    private int createdby;
    private Timestamp createdtime;
    private int statusid;
    private boolean active;
    private Integer assignee;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "commentid")
    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    @Basic
    @Column(name = "createdby")
    public int getCreatedby() {
        return createdby;
    }

    public void setCreatedby(int createdby) {
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
    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    @Basic
    @Column(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (id != that.id) return false;
        if (commentid != that.commentid) return false;
        if (createdby != that.createdby) return false;
        if (statusid != that.statusid) return false;
        if (active != that.active) return false;
        if (createdtime != null ? !createdtime.equals(that.createdtime) : that.createdtime != null) return false;
        if (assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + commentid;
        result = 31 * result + createdby;
        result = 31 * result + (createdtime != null ? createdtime.hashCode() : 0);
        result = 31 * result + statusid;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        return result;
    }
}
