package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.TicketconversationEntity;
import com.scc.ticketmanagement.Entities.TicketconversationEntityPK;
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
