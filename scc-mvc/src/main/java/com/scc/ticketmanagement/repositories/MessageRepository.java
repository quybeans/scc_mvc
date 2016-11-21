package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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


    @Query("SELECT m FROM MessageEntity m WHERE (m.receiverid =:receiverId and m.senderid =:senderId) " +
            "or (m.receiverid =:senderId and m.senderid =:receiverId)" +
            "order by m.seq asc")
    List<MessageEntity> getMessageAsc(@Param("receiverId") String receiverId,
                                      @Param("senderId") String senderId);

    @Query("SELECT m FROM MessageEntity m WHERE (m.receiverid =:receiverId and m.senderid =:senderId) " +
            "or (m.receiverid =:senderId and m.senderid =:receiverId)")
    Page<MessageEntity> getMessageWithPage(@Param("receiverId") String receiverId,
                                           @Param("senderId") String senderId, Pageable page);

    @Query("SELECT m FROM  MessageEntity m WHERE m.id = :id")
    public MessageEntity getMessageById(@Param("id") String id);


    @Query("UPDATE MessageEntity m set m.messageRead= true WHERE m.id=:id")
    @Modifying
    @Transactional
    void setMessageRead(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE MessageEntity m set m.messageRead = TRUE WHERE (m.receiverid =:receiverId and m.senderid =:senderId) " +
            "or (m.receiverid =:senderId and m.senderid =:receiverId)")
    void setConversationRead(@Param("receiverId") String receiverId,
                                            @Param("senderId") String senderId);

    @Query("SELECT count(m) FROM MessageEntity m WHERE m.messageRead = false AND ((m.receiverid =:receiverId and m.senderid =:senderId) " +
            "or (m.receiverid =:senderId and m.senderid =:receiverId))")
    Integer getNumberOfUnreadMessageInConversation(@Param("receiverId") String receiverId,
                                            @Param("senderId") String senderId);

    @Query("SELECT count(m) FROM MessageEntity m WHERE m.messageRead = FALSE AND m.receiverid = :receiverId")
    Integer getNumberOfUnreadMessageInPage(@Param("receiverId") String receiverId);

    @Query("SELECT count(m) FROM MessageEntity m where m.senderid IN (SELECT b.pageid FROM BrandpageEntity b where b.brandid =:brandId) or m.receiverid IN (SELECT b.pageid FROM BrandpageEntity b where b.brandid =:brandId)")
    Integer getNumberOfMessageInBrand(@Param("brandId") Integer brandId);

    @Query("SELECT count(m) FROM MessageEntity m where m.receiverid =:pageId or m.senderid =:pageId")
    Integer getNumberOfMessageInPage(@Param("pageId") String pageId);

}
