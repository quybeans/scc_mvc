package com.scc.ticketmanagement.extendEntity;

import com.scc.ticketmanagement.entity.TicketEntity;

/**
 * Created by user on 10/29/2016.
 */
public class ExtendTicket extends TicketEntity {
    private String content;
    private String createbyuser;
    private String assigneeuser;
    private String currentstatus;
    private String currentpriority;

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

    public String getCurrentpriority() {
        return currentpriority;
    }

    public void setCurrentpriority(String currentpriority) {
        this.currentpriority = currentpriority;
    }
}
