package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.UserMessageEntity;

/**
 * Created by Thien on 11/26/2016.
 */
public interface UserMessageService {
    UserMessageEntity create(String messageId, int userId);
    UserMessageEntity get(String messageId);

}
