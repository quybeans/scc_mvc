package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.TicketstatuschangeEntity;
import com.scc.ticketmanagement.Entities.TicketstatuschangeEntityPK;

import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
public interface TicketStatusChangeService {

    List<TicketstatuschangeEntity> findAll();
    void assignTicket(Integer userid,Integer ticketID);
    void createStatusChange(Integer userid, Integer ticketID, Integer statusid);
    List<TicketstatuschangeEntity> getTicketChanges(Integer ticketid);
}
