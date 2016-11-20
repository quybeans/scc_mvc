package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by quybeans on 21/11/2016.
 */
@Entity
@Table(name = "user_comment", schema = "scc", catalog = "")
@IdClass(UserCommentEntityPK.class)
public class UserCommentEntity {
    private String commentid;
    private int userid;
    private String postid;

    @Id
    @Column(name = "commentid")
    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    @Id
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCommentEntity that = (UserCommentEntity) o;

        if (userid != that.userid) return false;
        if (commentid != null ? !commentid.equals(that.commentid) : that.commentid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentid != null ? commentid.hashCode() : 0;
        result = 31 * result + userid;
        return result;
    }

    @Basic
    @Column(name = "postid")
    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
