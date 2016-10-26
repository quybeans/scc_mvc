package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.services.*;
import com.scc.ticketmanagement.utilities.Constant;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
@Controller
public class TicketController {
    @Autowired
    CommentService commentService;

    @Autowired
    TicketConversationService ticketConversationService;

    @Autowired
    TicketStatusChangeService ticketStatusChangeService;

    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;



    @RequestMapping("/Ticket")
    public String loadallTicket(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity user =(UserEntity) session.getAttribute("user");
        if(user.getRoleid()== Constant.ROLE_ADMIN){
            List<TicketEntity> lst = ticketService.findAll();
            model.addAttribute("ListTicket",lst);
        }else {
            List<TicketEntity> lstTicketUser=ticketService.getTicketUser(user.getUserid());
            model.addAttribute("ListTicket",lstTicketUser);
        }
        List<UserEntity> lstUser= userService.findAll();
        model.addAttribute("ListUser",lstUser);
        return "Ticket";
    }

    @RequestMapping("/createTicket")
    public String createTicket(HttpServletRequest request,
                               @RequestParam ("txtContent") String content){
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if(session!=null){
            ticketService.createTicket(user.getUserid());
            return "redirect:Ticket";
        }
        return "login";
    }


    @RequestMapping("/AssignTicket")
    public String assignTicket(@RequestParam("slUser") Integer userid,
                               @RequestParam("txtTicketID") Integer ticketid){
        ticketService.assignticket(ticketid,userid);
        ticketStatusChangeService.assignTicket(userid,ticketid);
        return "redirect:Ticket";
    }

    @RequestMapping("/ChangeTicketStatus")
    public String changeTicketStatus(@RequestParam("slStatus") Integer statusid,
                                     @RequestParam("txtTicketID") Integer ticketid,
                                     @RequestParam("txtAssignee") Integer assignee){
        ticketService.changeStatusTicket(ticketid,statusid);
        ticketStatusChangeService.createStatusChange(assignee,ticketid,statusid);
        return "redirect:Ticket";
    }

    @RequestMapping("/TicketRequest")
    public String ticketRequest(){

        return "a";
    }
    @RequestMapping("/TicketDetail")
    public String ticketDetail(@RequestParam ("ticketid") Integer ticketid,Model model){
        List<TicketstatuschangeEntity> lstTSE =ticketStatusChangeService.getTicketChanges(ticketid);
        List<TicketconversationEntity> lstTCE=ticketConversationService.getTicketConversation(ticketid);
        model.addAttribute("ListUser",userService.findAll());
        model.addAttribute("ListComment",commentService.findAll());
        model.addAttribute("ListStatus",lstTSE);
        model.addAttribute("ListConversation",lstTCE);
        return "TicketDetail";
    }
}
