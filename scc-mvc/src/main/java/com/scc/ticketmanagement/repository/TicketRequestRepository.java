package com.scc.ticketmanagement.repository;

import com.scc.ticketmanagement.entity.TicketrequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 10/27/2016.
 */
public interface TicketRequestRepository extends JpaRepository<TicketrequestEntity,Integer>{

}
