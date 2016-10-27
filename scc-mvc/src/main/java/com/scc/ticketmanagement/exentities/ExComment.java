package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.CommentEntity;

/**
 * Created by QuyBeans on 06-Oct-16.
 */
public class ExComment extends CommentEntity {
    private String createdByName;



    public String getCreatedByName() {return createdByName;}
    public void setCreatedByName(String createdByName) {this.createdByName = createdByName;}
}
