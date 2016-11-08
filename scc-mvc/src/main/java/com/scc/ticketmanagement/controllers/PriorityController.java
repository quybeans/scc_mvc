package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.PriorityEntity;
import com.scc.ticketmanagement.Entities.TicketEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.exentities.ExtendPriority;
import com.scc.ticketmanagement.repositories.PriorityReposioty;
import com.scc.ticketmanagement.repositories.TicketRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 10/30/2016.
 */
@RestController
public class PriorityController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PriorityReposioty priorityReposioty;

    @Autowired
    TicketRepository ticketRepository;

    @RequestMapping("/getallpriorityofbrand")
    public List<PriorityEntity> getallpriorityofbrand(HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);
        List<PriorityEntity> listpriority = priorityReposioty.findBybrandid(user.getBrandid());
        return listpriority;
    }

    @RequestMapping("/updatepriority")
    public PriorityEntity updatepriority(@RequestParam("priorityid") Integer priorityid,
                                         @RequestParam("priorityduration") Integer duration,
                                         @RequestParam("priorityname")String name){

        PriorityEntity priorityEntity = priorityReposioty.findOne(priorityid);
        if(!duration.equals("")){
            priorityEntity.setDuration(duration);
        }
        if(!name.equals("")){
            priorityEntity.setName(name);
        }

        return priorityReposioty.save(priorityEntity);

    }

    @RequestMapping("/createpriority")
    public PriorityEntity createpriority(@RequestParam("priorityname")String name,
                                         @RequestParam("priorityduration")Integer duration,HttpServletRequest request){

        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);
        PriorityEntity priorityEntity = new PriorityEntity();
        priorityEntity.setName(name);
        priorityEntity.setDuration(duration);
        priorityEntity.setBrandid(user.getBrandid());
        return priorityReposioty.save(priorityEntity);
    }

    @RequestMapping("/deletepriority")
    public void deletepriority(@RequestParam("priorityid") Integer priorityid){
        PriorityEntity priorityEntity = priorityReposioty.findOne(priorityid);
        if(priorityEntity!=null){
            priorityReposioty.delete(priorityid);
        }
    }

    @RequestMapping("/getticketduetime")
    public ExtendPriority getticketduetime(@RequestParam("ticketid") Integer ticketid){
        TicketEntity ticket = ticketRepository.findOne(ticketid);
        PriorityEntity priority = priorityReposioty.findOne(ticket.getPriority());
        ExtendPriority extendPriority = new ExtendPriority();
        extendPriority.setBrandid(priority.getBrandid());
        extendPriority.setDuration(priority.getDuration());
        extendPriority.setDuetime(ticket.getDuetime());
        extendPriority.setName(priority.getName());
        return extendPriority;
    }
}
