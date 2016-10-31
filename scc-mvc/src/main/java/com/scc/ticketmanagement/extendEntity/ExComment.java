package com.scc.ticketmanagement.extendEntity;

import com.scc.ticketmanagement.entity.CommentEntity;

/**
 * Created by QuyBeans on 06-Oct-16.
 */
public class ExComment extends CommentEntity {
    private String createdByName;



    public String getCreatedByName() {return createdByName;}
    public void setCreatedByName(String createdByName) {this.createdByName = createdByName;}
}
