package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.TicketrequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 10/27/2016.
 */
public interface TicketRequestRepository extends JpaRepository<TicketrequestEntity,Integer>{

}
