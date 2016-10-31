package com.scc.ticketmanagement.entity;

import javax.persistence.*;

/**
 * Created by Thien on 10/29/2016.
 */
@Entity
@Table(name = "userfacebookaccount", schema = "scc", catalog = "")
public class UserfacebookaccountEntity {
    private int id;
    private int userid;
    private int facebookaccountid;
    private boolean active;

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
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "facebookaccountid")
    public int getFacebookaccountid() {
        return facebookaccountid;
    }

    public void setFacebookaccountid(int facebookaccountid) {
        this.facebookaccountid = facebookaccountid;
    }

    @Basic
    @Column(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserfacebookaccountEntity that = (UserfacebookaccountEntity) o;

        if (id != that.id) return false;
        if (userid != that.userid) return false;
        if (facebookaccountid != that.facebookaccountid) return false;
        if (active != that.active) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userid;
        result = 31 * result + facebookaccountid;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
