package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by user on 10/30/2016.
 */
@Entity
@Table(name = "priority", schema = "scc", catalog = "")
public class PriorityEntity {
    private int id;
    private String name;
    private Integer duration;
    private Integer brandid;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "brandid")
    public Integer getBrandid() {
        return brandid;
    }

    public void setBrandid(Integer brandid) {
        this.brandid = brandid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriorityEntity that = (PriorityEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (brandid != null ? !brandid.equals(that.brandid) : that.brandid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (brandid != null ? brandid.hashCode() : 0);
        return result;
    }
}
