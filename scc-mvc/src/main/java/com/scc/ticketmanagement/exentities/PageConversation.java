package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.PageEntity;

/**
 * Created by Thien on 11/13/2016.
 */
public class PageConversation extends PageEntity {

    private int unreadMessage;

    public int getUnreadMessage() {
        return unreadMessage;
    }

    public void setUnreadMessage(int unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

}
