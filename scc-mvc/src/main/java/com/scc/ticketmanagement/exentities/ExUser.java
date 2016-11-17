package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.UserEntity;

/**
 * Created by QuyBean on 11/17/2016.
 */
public class ExUser extends UserEntity {
    private String createdByName;
    private String brandIdName;

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getBrandIdName() {
        return brandIdName;
    }

    public void setBrandIdName(String brandIdName) {
        this.brandIdName = brandIdName;
    }
}
