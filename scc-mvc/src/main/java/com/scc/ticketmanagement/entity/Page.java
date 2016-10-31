package com.scc.ticketmanagement.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by QuyBean on 10/26/2016.
 */
@Entity
public class Page {
    private String pageid;
    private String name;
    private String accesstoken;
    private String type;
    private Boolean active;

    @Id
    @Column(name = "pageid")
    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
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
    @Column(name = "accesstoken")
    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (pageid != null ? !pageid.equals(page.pageid) : page.pageid != null) return false;
        if (name != null ? !name.equals(page.name) : page.name != null) return false;
        if (accesstoken != null ? !accesstoken.equals(page.accesstoken) : page.accesstoken != null) return false;
        if (type != null ? !type.equals(page.type) : page.type != null) return false;
        if (active != null ? !active.equals(page.active) : page.active != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageid != null ? pageid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (accesstoken != null ? accesstoken.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }
}
