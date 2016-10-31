package com.scc.ticketmanagement.entity;

import javax.persistence.*;

/**
 * Created by Thien on 10/29/2016.
 */
@Entity
@Table(name = "page", schema = "scc", catalog = "")
public class PageEntity {
    private String pageid;
    private String name;
    private String accesstoken;
    private String type;
    private String category;
    private boolean crawler;
    private boolean active;

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
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "crawler")
    public boolean isCrawler() {
        return crawler;
    }

    public void setCrawler(boolean crawler) {
        this.crawler = crawler;
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

        PageEntity that = (PageEntity) o;

        if (crawler != that.crawler) return false;
        if (active != that.active) return false;
        if (pageid != null ? !pageid.equals(that.pageid) : that.pageid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (accesstoken != null ? !accesstoken.equals(that.accesstoken) : that.accesstoken != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pageid != null ? pageid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (accesstoken != null ? accesstoken.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (crawler ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
