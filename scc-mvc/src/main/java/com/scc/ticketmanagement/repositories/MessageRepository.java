package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.MessageEntity;
import org.hibernate.persister.entity.Queryable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Thien on 11/2/2016.
 */
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    @Query("SELECT concat(m.senderid,'_',m.receiverid) FROM MessageEntity m GROUP BY concat(m.senderid,'_',m.receiverid)")
    public List<MessageEntity> getAllConversations();

    @Query("SELECT m.senderid FROM  MessageEntity m WHERE m.receiverid=:pageId group by m.senderid")
    public List<String> getAllSenderIdByPageId(@Param("pageId") String pageId);

    //Get last message
    @Query("SELECT m FROM MessageEntity m WHERE (m.receiverid =:receiverId and m.senderid =:senderId) " +
            "or (m.receiverid =:senderId and m.senderid =:receiverId)" +
            "order by m.seq desc")
    List<MessageEntity> getMessageDesc(@Param("receiverId") String receiverId,
                                       @Param("senderId") String senderId);

}
