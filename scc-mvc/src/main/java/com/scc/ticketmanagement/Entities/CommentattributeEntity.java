package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by user on 10/30/2016.
 */
@Entity
@Table(name = "commentattribute", schema = "scc", catalog = "")
public class CommentattributeEntity {
    private int id;
    private String commentid;
    private Integer attributeid;

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
    @Column(name = "attributeid")
    public Integer getAttributeid() {
        return attributeid;
    }

    public void setAttributeid(Integer attributeid) {
        this.attributeid = attributeid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentattributeEntity that = (CommentattributeEntity) o;

        if (id != that.id) return false;
        if (commentid != null ? !commentid.equals(that.commentid) : that.commentid != null) return false;
        if (attributeid != null ? !attributeid.equals(that.attributeid) : that.attributeid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (commentid != null ? commentid.hashCode() : 0);
        result = 31 * result + (attributeid != null ? attributeid.hashCode() : 0);
        return result;
    }
}
