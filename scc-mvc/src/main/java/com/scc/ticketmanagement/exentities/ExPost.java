package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.PostEntity;

/**
 * Created by QuyBean on 10/26/2016.
 */
public class ExPost extends PostEntity {
    public String createdByName;
    public int negCount;
    public int posCount;

    public String getCreatedByName() {
        return createdByName;
    }
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public void setNegCount(int negCount) {
        this.negCount = negCount;
    }

    public void setPosCount(int posCount) {
        this.posCount = posCount;
    }

    public int getNegCount() {
        return negCount;
    }

    public int getPosCount() {
        return posCount;
    }
}
