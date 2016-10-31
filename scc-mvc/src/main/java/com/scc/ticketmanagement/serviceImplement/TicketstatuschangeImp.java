package com.scc.ticketmanagement.serviceImplement;

import com.scc.ticketmanagement.entity.TicketstatuschangeEntity;
import com.scc.ticketmanagement.repository.TicketStatusChangeRepository;
import com.scc.ticketmanagement.service.TicketStatusChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
@Service
public class TicketstatuschangeImp implements TicketStatusChangeService {


    @Autowired
    TicketStatusChangeRepository ticketStatusChangeRepository;

    @Override
    public List<TicketstatuschangeEntity> findAll() {
        return ticketStatusChangeRepository.findAll();
    }

    @Override
    public void assignTicket(Integer userid, Integer ticketID) {
        TicketstatuschangeEntity ticketstatuschangeEntity = new TicketstatuschangeEntity();
        ticketstatuschangeEntity.setChangeby(userid);
        ticketstatuschangeEntity.setTicketid(ticketID);
        ticketstatuschangeEntity.setStatusid(1);
        ticketstatuschangeEntity.setCreatedat(new Timestamp(new Date().getTime()));
        ticketStatusChangeRepository.save(ticketstatuschangeEntity);
    }

    @Override
    public void createStatusChange(Integer userid, Integer ticketID, Integer statusid) {
        TicketstatuschangeEntity ticketstatuschangeEntity = new TicketstatuschangeEntity();
        ticketstatuschangeEntity.setChangeby(userid);
        ticketstatuschangeEntity.setTicketid(ticketID);
        ticketstatuschangeEntity.setStatusid(statusid);
        ticketstatuschangeEntity.setCreatedat(new Timestamp(new Date().getTime()));
        ticketStatusChangeRepository.save(ticketstatuschangeEntity);
    }

    @Override
    public List<TicketstatuschangeEntity> getTicketChanges(Integer ticketid) {

        return ticketStatusChangeRepository.getTicketChanges(ticketid);
    }
}
