package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.TicketEntity;

/**
 * Created by user on 10/29/2016.
 */
public class ExtendTicket extends TicketEntity {
    private String content;
    private String createbyuser;
    private String assigneeuser;
    private String currentstatus;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatebyuser() {
        return createbyuser;
    }

    public void setCreatebyuser(String createbyuser) {
        this.createbyuser = createbyuser;
    }

    public String getAssigneeuser() {
        return assigneeuser;
    }

    public void setAssigneeuser(String assigneeuser) {
        this.assigneeuser = assigneeuser;
    }

    public String getCurrentstatus() {
        return currentstatus;
    }

    public void setCurrentstatus(String currentstatus) {
        this.currentstatus = currentstatus;
    }
}
