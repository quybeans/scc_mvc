package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by QuyBean on 10/27/2016.
 */
@Entity
@Table(name = "ticket", schema = "scc", catalog = "")
public class TicketEntity {
    private int id;
    private String commentid;
    private Integer createdby;
    private Timestamp createdtime;
    private Integer statusid;
    private Boolean active;
    private Integer assignee;
    private String deadline;

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
    @Column(name = "commentid")
    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
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
    @Column(name = "deadline")
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (id != that.id) return false;
        if (commentid != null ? !commentid.equals(that.commentid) : that.commentid != null) return false;
        if (createdby != null ? !createdby.equals(that.createdby) : that.createdby != null) return false;
        if (createdtime != null ? !createdtime.equals(that.createdtime) : that.createdtime != null) return false;
        if (statusid != null ? !statusid.equals(that.statusid) : that.statusid != null) return false;
        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (assignee != null ? !assignee.equals(that.assignee) : that.assignee != null) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (commentid != null ? commentid.hashCode() : 0);
        result = 31 * result + (createdby != null ? createdby.hashCode() : 0);
        result = 31 * result + (createdtime != null ? createdtime.hashCode() : 0);
        result = 31 * result + (statusid != null ? statusid.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        return result;
    }
}
