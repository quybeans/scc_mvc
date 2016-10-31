package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by user on 10/29/2016.
 */
@Entity
@Table(name = "attribute", schema = "scc", catalog = "")
public class AttributeEntity {
    private int id;
    private String name;
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

        AttributeEntity that = (AttributeEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (brandid != null ? !brandid.equals(that.brandid) : that.brandid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (brandid != null ? brandid.hashCode() : 0);
        return result;
    }
}
