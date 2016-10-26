package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
@Entity
@Table(name = "facebookaccount", schema = "scc", catalog = "")
public class FacebookaccountEntity {
    private int facebookaccountid;
    private String facebookuserid;
    private String accesstoken;
    private boolean active;
    private int userid;

    @Id
    @Column(name = "facebookaccountid")
    public int getFacebookaccountid() {
        return facebookaccountid;
    }

    public void setFacebookaccountid(int facebookaccountid) {
        this.facebookaccountid = facebookaccountid;
    }

    @Basic
    @Column(name = "facebookuserid")
    public String getFacebookuserid() {
        return facebookuserid;
    }

    public void setFacebookuserid(String facebookuserid) {
        this.facebookuserid = facebookuserid;
    }

    @Basic
    @Column(name = "accesstoken")
    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
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

        FacebookaccountEntity that = (FacebookaccountEntity) o;

        if (facebookaccountid != that.facebookaccountid) return false;
        if (active != that.active) return false;
        if (facebookuserid != null ? !facebookuserid.equals(that.facebookuserid) : that.facebookuserid != null)
            return false;
        if (accesstoken != null ? !accesstoken.equals(that.accesstoken) : that.accesstoken != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = facebookaccountid;
        result = 31 * result + (facebookuserid != null ? facebookuserid.hashCode() : 0);
        result = 31 * result + (accesstoken != null ? accesstoken.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Basic
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
