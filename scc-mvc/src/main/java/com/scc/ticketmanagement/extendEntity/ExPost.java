package com.scc.ticketmanagement.extendEntity;

import com.scc.ticketmanagement.entity.PostEntity;

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
