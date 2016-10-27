package com.scc.ticketmanagement.controllers;


import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.exentities.TicketDetail;
import com.scc.ticketmanagement.exentities.TicketHistory;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.utilities.Constant;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private  ProfileRepository profileRepository;

    @Autowired
    private  TicketRepository ticketRepository;

    @Autowired
    private  TicketStatusChangeRepository ticketStatusChangeRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TicketRequestRepository ticketRequestRepository;

    @RequestMapping("/createticket")
    @ResponseBody
    public TicketEntity createTicket(HttpServletRequest request,
                                     @RequestParam("commentid") String commentid,
                                     @RequestParam("deadline") String deadline,
                                     @RequestParam("assignee") Integer assignee){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        if(session!=null){
            //Tao moi 1 ticket
            TicketEntity ticketEntity = new TicketEntity();
            ticketEntity.setCreatedby(user.getUserid());
            ticketEntity.setCommentid(commentid);
            ticketEntity.setStatusid(1);
            ticketEntity.setAssignee(assignee);
            ticketEntity.setDeadline(deadline);
            ticketEntity.setActive(true);
            ticketEntity.setStatusid(2);
            ticketEntity.setCreatedtime(new Timestamp(new Date().getTime()));
            TicketEntity ticket= ticketRepository.save(ticketEntity);

            //Tao 1 ticket history
            TicketstatuschangeEntity status = new TicketstatuschangeEntity();
            status.setTicketid(ticket.getId());
            status.setStatusid(2);
            status.setChangeby(user.getUserid());
            status.setAssignee(assignee);
            status.setCreatedat(new Timestamp(new Date().getTime()));

            ticketStatusChangeRepository.save(status);
        }
        return null;
    }


    @RequestMapping("/assignticket")
    @ResponseBody
    public TicketEntity assignTicket(@RequestParam("commentid") String commentid,
                                     @RequestParam("assignee") Integer assignee, HttpServletRequest request){
        //Lay userid cua account dang login vao he thong
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);
        TicketEntity ticket =ticketRepository.findBycommentid(commentid);
        //Khi role = supervisor
        if(user.getRoleid()==Constant.ROLE_SUPERVISOR){
            //Thay doi assignee
            ticket.setAssignee(assignee);

            //Luu 1 TicketHistory
            TicketstatuschangeEntity status = new TicketstatuschangeEntity();
            status.setTicketid(ticket.getId());
            status.setStatusid(2);
            status.setChangeby(user.getUserid());
            status.setAssignee(assignee);
            status.setCreatedat(new Timestamp(new Date().getTime()));
            ticketStatusChangeRepository.save(status);

        }//Khi role = staff(vi admin khong vao trang nay nen chi else)
        else{
            TicketrequestEntity ticketrequestEntity = new TicketrequestEntity();
            ticketrequestEntity.setAssigner(user.getUserid());
            ticketrequestEntity.setAssignee(assignee);
            ticketrequestEntity.setRequestat(new Timestamp(new Date().getTime()));
            ticketrequestEntity.setTicketid(ticket.getId());
            ticketRequestRepository.save(ticketrequestEntity);
        }


        return ticketRepository.save(ticket);
    }

    @RequestMapping("/changeticketstatus")
    @ResponseBody
    public TicketEntity changeTicketStatus(@RequestParam("commentid") String commentid,
                                           @RequestParam("status") Integer status, HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        //Thay doi status trong ticket
        TicketEntity ticket=ticketRepository.findBycommentid(commentid);
        ticket.setStatusid(status);

        //Them 1 ticket history
        TicketstatuschangeEntity statuschange = new TicketstatuschangeEntity();
        statuschange.setTicketid(ticket.getId());
        statuschange.setChangeby(user.getUserid());
        statuschange.setStatusid(ticket.getStatusid());
        statuschange.setCreatedat(new Timestamp(new Date().getTime()));
        ticketStatusChangeRepository.save(statuschange);

        return ticketRepository.save(ticket);
    }



    @RequestMapping("/TicketDetail")
    public String ticketDetail(@RequestParam("ticketid") Integer ticketid, Model model){

        return "TicketDetail";
    }



    @RequestMapping("/getticket")
    @ResponseBody
    public TicketDetail getTicket(@RequestParam("commentid") String commentid){
        TicketEntity ticket = ticketRepository.findBycommentid(commentid);
        TicketDetail detail = new TicketDetail();

        //Get Full name cua nguoi tao ra ticket
        ProfileEntity profileEntity = new ProfileEntity();
        if(ticket.getCreatedby()!=null){
            profileEntity = profileRepository.findOne(userRepository.findOne(ticket.getCreatedby()).getProfileid());
            detail.setCreateby(profileEntity.getFirstname()+ " " + profileEntity.getLastname());
        }

        //Get Full name cua nguoi duoc assign ticket
        if(ticket.getAssignee()!=null){
            profileEntity= profileRepository.findOne(userRepository.findOne(ticket.getAssignee()).getProfileid());

            detail.setAssignee(profileEntity.getFirstname() + " " + profileEntity.getLastname());
        }

        detail.setActive(ticket.getActive());
        detail.setCommentid(ticket.getCommentid());
        detail.setCreatedtime(ticket.getCreatedtime());
        detail.setDeadline(ticket.getDeadline());
        detail.setId(ticket.getId());

        switch (ticket.getStatusid()){
            case Constant.STATUS_UNASSIGN: detail.setStatusid("Unassign"); break;
            case Constant.STATUS_ASSIGN: detail.setStatusid("Assign"); break;
            case Constant.STATUS_OPEN: detail.setStatusid("Open"); break;
            case Constant.STATUS_INPROCESS: detail.setStatusid("Inprocess"); break;
            case Constant.STATUS_SOLVING: detail.setStatusid("Solving"); break;
            case Constant.STATUS_SOLVED: detail.setStatusid("Solved"); break;
            case Constant.STATUS_CLOSE: detail.setStatusid("Close"); break;
        }

        return detail;
    }

    @RequestMapping("/gettickethistory")
    @ResponseBody
    public List<TicketHistory> getTickethistory(@RequestParam("commentid") String commentid){
        TicketEntity ticket = ticketRepository.findBycommentid(commentid);
        if(ticket!=null){
            List<TicketstatuschangeEntity> list = ticketStatusChangeRepository.getTicketChanges(ticket.getId());
            List<TicketHistory> history = new ArrayList<TicketHistory>();
            if(list!=null){
                for (TicketstatuschangeEntity tk: list) {
                    TicketHistory th= new TicketHistory();

                    //Get Full name cua user thay doi trang thai ticket
                    ProfileEntity pro;
                    if(tk.getChangeby()!=null){
                        pro = profileRepository.findOne(userRepository.findOne(tk.getChangeby()).getProfileid());
                        th.setUserid(pro.getFirstname() + " " + pro.getLastname());
                    }


                    //Get Full name cua nguoi duoc assign
                    if(tk.getAssignee()!=null){
                        pro=profileRepository.findOne(userRepository.findOne(tk.getAssignee()).getProfileid());
                        th.setAssignee(pro.getFirstname() + " " + pro.getLastname());
                    }


                    th.setCreatedat(tk.getCreatedat());
                    th.setStatusid(tk.getStatusid());
                    th.setTicketid(tk.getTicketid());
                    history.add(th);
                }
            }

            return history;
        }
        return null;
    }

    @RequestMapping("/getconversation")
    @ResponseBody
    public List<CommentEntity> getConversation(@RequestParam("commentid") String commentid){
        List<CommentEntity> list = commentRepository.findCommentByPostId(commentid);
        return list;
    }
}
