package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.TicketconversationEntityPK;
import com.scc.ticketmanagement.Entities.TicketstatuschangeEntity;
import com.scc.ticketmanagement.Entities.TicketstatuschangeEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
public interface TicketStatusChangeRepository extends JpaRepository<TicketstatuschangeEntity,TicketstatuschangeEntityPK> {

    @Query("select e from TicketstatuschangeEntity e where e.ticketid=:ticketid")
     List<TicketstatuschangeEntity> getTicketChanges(@Param("ticketid") Integer ticketid);
}
