package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.PostEntity;

/**
 * Created by QuyBean on 10/26/2016.
 */
public class ExPost extends PostEntity {
    public String createdByName;


    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
}