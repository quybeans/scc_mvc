package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.exentities.ExtendTicketRequest;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 11/10/2016.
 */
@RestController
public class TickRequestController {
    @Autowired
    TicketStatusChangeRepository ticketStatusChangeRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    TicketRequestRepository ticketRequestRepository;

    @RequestMapping("/getticketrequest")
    public List<ExtendTicketRequest> getticketrequest(@RequestParam("assignee") Integer assignee){
        List<TicketrequestEntity> listrequest = ticketRequestRepository.getTicketRequestOfUser(assignee);
        List<ExtendTicketRequest> extendrequestlist = new ArrayList<ExtendTicketRequest>();
        for (TicketrequestEntity request: listrequest) {
            ExtendTicketRequest extendrequest = new ExtendTicketRequest();
            extendrequest.setTicketid(request.getTicketid());
            extendrequest.setNote(request.getNote());
            extendrequest.setAssignee(request.getAssignee());
            extendrequest.setAssigner(request.getAssigner());
            extendrequest.setId(request.getId());
            extendrequest.setRequestat(request.getRequestat());

            ProfileEntity pro = profileRepository.findOne(userRepository.findOne(request.getAssigner()).getUserid());
            extendrequest.setAssignername(pro.getFirstname()+ " " + pro.getLastname());

            TicketEntity ticket = ticketRepository.findOne(request.getTicketid());
            extendrequest.setTicketname(ticket.getName());

            extendrequestlist.add(extendrequest);
        }
        return extendrequestlist;
    }

    @RequestMapping("/acceptrequest")
    public void acceptrequest(@RequestParam("requestid") Integer requestid){
        TicketrequestEntity request = ticketRequestRepository.findOne(requestid);
        TicketEntity ticket = ticketRepository.findOne(request.getTicketid());
        ticket.setAssignee(request.getAssignee());
        ticket.setStatusid(Constant.STATUS_ASSIGN);
        ticket.setDuetime(new Timestamp(new Date().getTime()));

        TicketstatuschangeEntity status = new TicketstatuschangeEntity();
        status.setTicketid(ticket.getId());
        status.setStatusid(Constant.STATUS_FORWARD);
        status.setChangeby(request.getAssigner());
        status.setAssignee(request.getAssignee());
        status.setCreatedat(new Timestamp(new Date().getTime()));
        status.setNote(request.getNote());
        ticketStatusChangeRepository.save(status);
        ticketRepository.save(ticket);
        ticketRequestRepository.delete(request);
    }

    @RequestMapping("/denirequest")
    public void denirequest(@RequestParam("requestid") Integer requestid){
        TicketrequestEntity request = ticketRequestRepository.findOne(requestid);
        ticketRequestRepository.delete(request);
    }

    @RequestMapping("/getticketrequestcount")
    public int getticketrequestcount(HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity loginuser = userRepository.findUserByUsername(loginUser);

        return ticketRequestRepository.getUserRequestCount(loginuser.getUserid());
    }
}
