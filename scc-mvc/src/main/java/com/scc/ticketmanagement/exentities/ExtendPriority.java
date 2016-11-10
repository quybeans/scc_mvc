package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.PriorityEntity;

import java.sql.Timestamp;

/**
 * Created by user on 11/3/2016.
 */
public class ExtendPriority extends PriorityEntity {
    private Timestamp duetime;

    public Timestamp getDuetime() {
        return duetime;
    }

    public void setDuetime(Timestamp duetime) {
        this.duetime = duetime;
    }
}
