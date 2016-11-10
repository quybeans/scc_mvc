package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.TicketrequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 10/27/2016.
 */
public interface TicketRequestRepository extends JpaRepository<TicketrequestEntity,Integer>{

    @Query("select t from TicketrequestEntity t where t.assignee=:assignee")
    public List<TicketrequestEntity> getTicketRequestOfUser(@Param("assignee") Integer assignee);
}
