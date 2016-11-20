package com.scc.ticketmanagement.Entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by quybeans on 21/11/2016.
 */
public class UserCommentEntityPK implements Serializable {
    private String commentid;
    private int userid;

    @Column(name = "commentid")
    @Id
    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    @Column(name = "userid")
    @Id
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

        UserCommentEntityPK that = (UserCommentEntityPK) o;

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
}
