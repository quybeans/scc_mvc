package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by Thien on 11/19/2016.
 */
@Entity
@Table(name = "brandpage", schema = "scc", catalog = "")
public class BrandpageEntity {
    private int brandpageid;
    private int brandid;
    private String pageid;
    private boolean manage;
    private boolean crawl;

    @Id
    @Column(name = "brandpageid")
    public int getBrandpageid() {
        return brandpageid;
    }

    public void setBrandpageid(int brandpageid) {
        this.brandpageid = brandpageid;
    }

    @Basic
    @Column(name = "brandid")
    public int getBrandid() {
        return brandid;
    }

    public void setBrandid(int brandid) {
        this.brandid = brandid;
    }

    @Basic
    @Column(name = "pageid")
    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    @Basic
    @Column(name = "manage")
    public boolean isManage() {
        return manage;
    }

    public void setManage(boolean manage) {
        this.manage = manage;
    }

    @Basic
    @Column(name = "crawl")
    public boolean isCrawl() {
        return crawl;
    }

    public void setCrawl(boolean crawl) {
        this.crawl = crawl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrandpageEntity that = (BrandpageEntity) o;

        if (brandpageid != that.brandpageid) return false;
        if (brandid != that.brandid) return false;
        if (manage != that.manage) return false;
        if (crawl != that.crawl) return false;
        if (pageid != null ? !pageid.equals(that.pageid) : that.pageid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = brandpageid;
        result = 31 * result + brandid;
        result = 31 * result + (pageid != null ? pageid.hashCode() : 0);
        result = 31 * result + (manage ? 1 : 0);
        result = 31 * result + (crawl ? 1 : 0);
        return result;
    }
}
