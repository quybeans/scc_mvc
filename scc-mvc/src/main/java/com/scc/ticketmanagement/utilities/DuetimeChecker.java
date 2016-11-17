package com.scc.ticketmanagement.utilities;

import com.scc.ticketmanagement.Entities.PriorityEntity;
import com.scc.ticketmanagement.Entities.TicketEntity;
import com.scc.ticketmanagement.Entities.TicketstatuschangeEntity;
import com.scc.ticketmanagement.repositories.PriorityReposioty;
import com.scc.ticketmanagement.repositories.TicketRepository;
import com.scc.ticketmanagement.repositories.TicketStatusChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 11/16/2016.
 */
@Component
public class DuetimeChecker {
    @Autowired
    private TicketStatusChangeRepository ticketStatusChangeRepository;

    @Autowired
    private PriorityReposioty priorityReposioty;

    @Autowired
    private TicketRepository ticketRepository;

    @Scheduled(fixedDelay = 300000)//5 phut
    public void duetimecheck(){
        System.out.println("Checker run at:"+new Date());
        List<TicketEntity> ticketlist= ticketRepository.findAll();
        for (TicketEntity ticket:ticketlist) {
            if(ticket.getStatusid()==Constant.STATUS_INPROCESS){
                PriorityEntity priority = priorityReposioty.findOne(ticket.getPriority());
                Long duetime = ticket.getDuetime().getTime() + (priority.getDuration()*60*1000);
                Long currenttime = new Date().getTime();
                if(duetime-currenttime<0) {
                    List<PriorityEntity> nextpriority = priorityReposioty.getNextPriority(priority.getDuration());
                    if(nextpriority.size()>0){
                        System.out.println(ticket.getName()+" bi nang priority");
                        ticket.setPriority(nextpriority.get(0).getId());
                        ticket.setDuetime(new Timestamp(new Date().getTime()));
                        ticket.setNote("This ticket is expired,System change priority to "+nextpriority.get(0).getName());
                        ticketRepository.save(ticket);

                        TicketstatuschangeEntity change = new TicketstatuschangeEntity();
                        change.setNote("This ticket is expired,System change priority to "+nextpriority.get(0).getName());
                        change.setPriorityid(nextpriority.get(0).getId());
                        change.setTicketid(ticket.getId());
                        change.setCreatedat(new Timestamp(new Date().getTime()));
                        change.setChangeby(0);
                        ticketStatusChangeRepository.save(change);
                    }else{
                        ticket.setStatusid(Constant.STATUS_EXPIRED);
                        ticket.setNote("This ticket is expired");
                        ticketRepository.save(ticket);

                        TicketstatuschangeEntity changes = new TicketstatuschangeEntity();
                        changes.setNote(" ");
                        changes.setAssignee(ticket.getCreatedby());
                        changes.setStatusid(Constant.STATUS_EXPIRED);
                        changes.setTicketid(ticket.getId());
                        changes.setChangeby(0);
                        changes.setCreatedat(new Timestamp(new Date().getTime()));
                        ticketStatusChangeRepository.save(changes);
                    }
                }
            }

        }
    }

}
