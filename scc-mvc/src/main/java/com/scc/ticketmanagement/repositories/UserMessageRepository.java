package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.UserMessageEntity;
import com.scc.ticketmanagement.Entities.UserMessageEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Thien on 11/26/2016.
 */
public interface UserMessageRepository extends JpaRepository<UserMessageEntity, UserMessageEntityPK> {

    @Query("select u from UserMessageEntity u where u.messageId =:messageId")
    UserMessageEntity getUserMessageByMessageId(@Param("messageId") String messageId);

}
