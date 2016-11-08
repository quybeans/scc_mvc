package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.MessageitemEntity;

/**
 * Created by Thien on 11/8/2016.
 */
public interface MessageItemService {
    public MessageitemEntity get(int id);
    public MessageitemEntity update(MessageitemEntity messageitemEntity);
}
