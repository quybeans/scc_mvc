package com.scc.ticketmanagement.serviceImplement;

import com.scc.ticketmanagement.entity.TicketEntity;
import com.scc.ticketmanagement.repository.TicketRepository;
import com.scc.ticketmanagement.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
@Service
public class TicketImp implements TicketService {
    @Autowired
    TicketRepository ticketRepository;


    @Override
    public List<TicketEntity> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public void changeStatusTicket(Integer ticketID,Integer statusID) {
        TicketEntity ticketEntity = ticketRepository.findOne(ticketID);
        ticketEntity.setStatusid(statusID);
        ticketRepository.save(ticketEntity);
    }

    @Override
    public void deactiveTicket(Integer ticketID) {
            TicketEntity ticketEntity = ticketRepository.findOne(ticketID);
        ticketEntity.setActive(false);
        ticketRepository.save(ticketEntity);

    }

    @Override
    public void createTicket(Integer createby) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setCreatedby(createby);
        ticketEntity.setActive(true);
        ticketEntity.setStatusid(1);
        ticketEntity.setCreatedtime(new Timestamp(new Date().getTime()));
        ticketRepository.save(ticketEntity);
    }

    @Override
    public void assignticket(Integer ticketid,Integer assignee){
        ticketRepository.assignTicket(ticketid,assignee);
    }

    @Override
    public TicketEntity getTicketByID(Integer ticketid){
        return ticketRepository.findOne(ticketid);
    }

    @Override
    public List<TicketEntity> getTicketUser(Integer userid) {
        return ticketRepository.getTicketUser(userid);
    }


}
