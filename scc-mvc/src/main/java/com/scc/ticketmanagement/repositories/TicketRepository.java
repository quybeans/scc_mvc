package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.Ticket;

import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
public interface TicketRepository extends JpaRepository<TicketEntity,Integer>,JpaSpecificationExecutor<TicketEntity> {

    @Query("update TicketEntity set assignee=:assignee WHERE id=:id")
    @Modifying
    @Transactional
    void assignTicket(@Param("id") Integer id,@Param("assignee")Integer assignee);

    @Query("select t from TicketEntity t where t.assignee=:userid")
    List<TicketEntity> getTicketUser(@Param("userid") Integer userid);

    @Query("select t from TicketEntity t where t.brandId=:brandid ORDER BY t.priority ASC")
    List<TicketEntity> getTicketOrderByPriority(@Param("brandid") Integer brandid);

    @Query("select t from TicketEntity t where t.assignee=:assignee and t.brandId=:brandid ORDER BY t.priority ASC ")
    List<TicketEntity> getStaffTicketOrderByPriority(@Param("assignee") Integer assignee,
                                                     @Param("brandid") Integer brandid);

    @Query("select t from TicketEntity t where t.assignee=:userid or t.createdby=:userid")
    List<TicketEntity> getListTicketOfUser(@Param("userid") Integer userid);


    @Query("SELECT t FROM TicketEntity t where t.id IN (SELECT i.ticketid FROM TicketitemEntity i where i.messageid IN (SELECT m.itemId FROM MessageitemEntity m where messageIdStart = :messageId))")
    List<TicketEntity> getTicketsByMessageId(@Param("messageId") String messageId);

    @Query("select COUNT(t) from TicketEntity t where t.brandId=:brandid")
    long  coutnTicketByBrandId(@Param("brandid") Integer brandid);

}
