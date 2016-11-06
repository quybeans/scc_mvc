package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.PriorityEntity;

import java.sql.Timestamp;

/**
 * Created by user on 11/3/2016.
 */
public class ExtendPriority extends PriorityEntity {
    private Timestamp createdtime;

    public Timestamp getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Timestamp createdtime) {
        this.createdtime = createdtime;
    }
}
