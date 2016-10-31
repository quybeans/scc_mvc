package com.scc.ticketmanagement.repository;

import com.scc.ticketmanagement.entity.TicketconversationEntity;
import com.scc.ticketmanagement.entity.TicketconversationEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
public interface TicketConversationRepository extends JpaRepository<TicketconversationEntity,TicketconversationEntityPK> {
    @Query("select e from TicketconversationEntity e where e.ticketid=:ticketid")
    List<TicketconversationEntity> getTicketConversation(@Param("ticketid") Integer ticketid);

}
