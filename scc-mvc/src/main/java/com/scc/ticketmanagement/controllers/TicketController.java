package com.scc.ticketmanagement.controllers;


import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.exentities.*;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.utilities.Constant;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by user on 9/30/2016.
 */
@RestController
public class TicketController {
    @Autowired
    PriorityReposioty priorityReposioty;

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

    @Autowired
    private CommentattributeRepository commentattributeRepository;

    @Autowired
    private TicketitemRepository ticketitemRepository;

    @RequestMapping("/createticket")
    public TicketEntity createTicket(HttpServletRequest request,
                                     @RequestParam("ticketname") String ticketname,
                                     @RequestParam("assignee") Integer assignee,
                                     @RequestParam("priority") Integer priority,
                                     @RequestParam("note") String note){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        if(session!=null){
            //Tao moi 1 ticket
            TicketEntity ticketEntity = new TicketEntity();
            ticketEntity.setCreatedby(user.getUserid());
            ticketEntity.setName(ticketname);
            ticketEntity.setStatusid(1);
            ticketEntity.setAssignee(assignee);
            ticketEntity.setActive(true);
            ticketEntity.setStatusid(2);
            ticketEntity.setPriority(priority);
            ticketEntity.setCreatedtime(new Timestamp(new Date().getTime()));
            ticketEntity.setNote(note);


            TicketEntity ticket= ticketRepository.save(ticketEntity);
            //Tao 1 ticket history
            TicketstatuschangeEntity status = new TicketstatuschangeEntity();
            status.setTicketid(ticket.getId());
            status.setStatusid(2);
            status.setChangeby(user.getUserid());
            status.setAssignee(assignee);
            status.setCreatedat(new Timestamp(new Date().getTime()));

            ticketStatusChangeRepository.save(status);

            return ticket;
        }
        return null;
    }

    @RequestMapping(value = "/getallticket",method = RequestMethod.GET,produces = "application/json")
    public List<ExtendTicket> getallticket(HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        List<TicketEntity> listticket = new ArrayList<TicketEntity>();
        if(user.getRoleid()!=Constant.ROLE_STAFF){
            listticket = ticketRepository.getTicketOrderByPriority();
        }else {
            listticket=ticketRepository.getStaffTicketOrderByPriority(user.getUserid());
        }


        List<ExtendTicket> listextendticket = new ArrayList<ExtendTicket>();
        //Tao mot extendticket list de show len bang
        for (TicketEntity tk: listticket) {
            ExtendTicket extendticket = new ExtendTicket();

            extendticket.setAssignee(tk.getAssignee());
            extendticket.setName(tk.getName());
            extendticket.setActive(tk.getActive());
            extendticket.setCreatedby(tk.getCreatedby());
            extendticket.setId(tk.getId());
            extendticket.setStatusid(tk.getStatusid());
            extendticket.setCreatedtime(tk.getCreatedtime());
            extendticket.setNote(tk.getNote());
            //Get UserEntity cua ng tao ra ticket
            UserEntity createTicketUser =userRepository.findOne(extendticket.getCreatedby());
            //Get ProfileEntity cua ng tao ra ticket
            ProfileEntity createTicketProfile = profileRepository.findOne(createTicketUser.getProfileid());
            //Get Fullname cua ng ta ticket de set vao extendTicket
            extendticket.setCreatebyuser(createTicketProfile.getFirstname() + " " +createTicketProfile.getLastname());

            //Get UserEntity cua ng duoc assign  ticket
            UserEntity assigneeUser =userRepository.findOne(extendticket.getAssignee());
            //Get ProfileEntity cua ng duoc assign  ticket
            ProfileEntity assigneeTicketProfile = profileRepository.findOne(assigneeUser.getProfileid());
            //Get Fullname cua ng ta ticket de set vao extendTicket
            extendticket.setAssigneeuser(assigneeTicketProfile.getFirstname() + " " + assigneeTicketProfile.getLastname());

            switch (extendticket.getStatusid()){
                case Constant.STATUS_UNASSIGN: extendticket.setCurrentstatus("Unassign"); break;
                case Constant.STATUS_ASSIGN: extendticket.setCurrentstatus("Assign"); break;
                case Constant.STATUS_OPEN: extendticket.setCurrentstatus("Open"); break;
                case Constant.STATUS_INPROCESS: extendticket.setCurrentstatus("Inprocess"); break;
                case Constant.STATUS_SOLVING: extendticket.setCurrentstatus("Solving"); break;
                case Constant.STATUS_SOLVED: extendticket.setCurrentstatus("Solved"); break;
                case Constant.STATUS_CLOSE: extendticket.setCurrentstatus("Close"); break;
            }

            //lay ten priority cua ticket
            PriorityEntity priority = priorityReposioty.findOne(tk.getPriority());
            extendticket.setCurrentpriority(priority.getName());
            listextendticket.add(extendticket);

        }
        return listextendticket;
    }


    @RequestMapping("/assignticket")
    public TicketEntity assignTicket(@RequestParam("ticketid") Integer ticketid,
                                     @RequestParam("assignee") Integer assignee,
                                     @RequestParam("assignnote") String assignnote,
                                     HttpServletRequest request){
        //Lay userid cua account dang login vao he thong
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        TicketEntity ticket =ticketRepository.findOne(ticketid);
        //Thay doi assignee
        ticket.setAssignee(assignee);
        ticket.setNote(assignnote);

        //Luu 1 TicketHistory
        TicketstatuschangeEntity status = new TicketstatuschangeEntity();
        status.setTicketid(ticket.getId());
        status.setStatusid(2);
        status.setChangeby(user.getUserid());
        status.setAssignee(assignee);
        status.setCreatedat(new Timestamp(new Date().getTime()));
        status.setNote(assignnote);
        ticketStatusChangeRepository.save(status);

        return ticketRepository.save(ticket);
    }

    @RequestMapping("/changeticketstatus")
    public TicketEntity changeTicketStatus(@RequestParam("ticketid") Integer ticketid,
                                           @RequestParam("status") Integer status,
                                           @RequestParam("statusnote") String statusnote,
                                           HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        //Thay doi status trong ticket
        TicketEntity ticket=ticketRepository.findOne(ticketid);
        ticket.setStatusid(status);
        ticket.setNote(statusnote);

        //Them 1 ticket history
        TicketstatuschangeEntity statuschange = new TicketstatuschangeEntity();
        statuschange.setTicketid(ticket.getId());
        statuschange.setChangeby(user.getUserid());
        statuschange.setStatusid(ticket.getStatusid());
        statuschange.setCreatedat(new Timestamp(new Date().getTime()));
        statuschange.setNote(statusnote);
        ticketStatusChangeRepository.save(statuschange);

        return ticketRepository.save(ticket);
    }

    @RequestMapping("/getticket")
    public TicketDetail getTicket(@RequestParam("commentid") String commentid){
        TicketitemEntity item = ticketitemRepository.getTicketItemByCommentID(commentid);
        TicketEntity ticket = ticketRepository.findOne(item.getTicketid());
        if(ticket!=null){

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
            detail.setCreatedtime(ticket.getCreatedtime());
            detail.setId(ticket.getId());
            detail.setName(ticket.getName());
            detail.setNote(ticket.getNote());
            //get priority name
            PriorityEntity priority = priorityReposioty.findOne(ticket.getPriority());
            detail.setPriority(priority.getName());
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
        return null;
    }

    @RequestMapping("/getticketdetail")
    public ExtendTicket getticketdetail(@RequestParam("ticketid") Integer ticketid){
        TicketEntity ticket = ticketRepository.findOne(ticketid);
        ExtendTicket extendTicket = new ExtendTicket();

        PriorityEntity pri = priorityReposioty.findOne(ticket.getPriority());
        extendTicket.setCurrentpriority(pri.getName());

        ProfileEntity profileEntity = profileRepository.findOne(userRepository.findOne(ticket.getAssignee()).getProfileid());
        extendTicket.setAssigneeuser(profileEntity.getFirstname());

        profileEntity = profileRepository.findOne(userRepository.findOne(ticket.getCreatedby()).getProfileid());
        extendTicket.setCreatebyuser(profileEntity.getFirstname());

        extendTicket.setCreatedtime(ticket.getCreatedtime());
        extendTicket.setName(ticket.getName());
        extendTicket.setNote(ticket.getNote());
        extendTicket.setActive(ticket.getActive());
        extendTicket.setCreatedby(ticket.getCreatedby());
        extendTicket.setAssignee(ticket.getAssignee());
        extendTicket.setStatusid(ticket.getStatusid());
        extendTicket.setId(ticket.getId());
        extendTicket.setPriority(ticket.getPriority());

        return extendTicket;
    }

    @RequestMapping("/gettickethistory")
    public List<TicketHistory> getTickethistory(@RequestParam("ticketid") Integer ticketid){
        TicketEntity ticket = ticketRepository.findOne(ticketid);
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
                    th.setNote(tk.getNote());
                    history.add(th);
                }
            }
            return history;
        }
        return null;
    }

    @RequestMapping("/getconversation")
    public List<CommentEntity> getConversation(@RequestParam("commentid") String commentid){
        List<CommentEntity> list = commentRepository.findCommentByPostId(commentid);
        return list;
    }

    @RequestMapping("/updateticket")
    public TicketEntity updateticket(@RequestParam("ticketid") Integer ticketid,
                                     @RequestParam("ticketnote") String ticketnote,
                                     @RequestParam("ticketpriority") Integer ticketpriority){
        TicketEntity ticket = ticketRepository.findOne(ticketid);
        ticket.setNote(ticketnote);
        ticket.setPriority(ticketpriority);
        return ticketRepository.save(ticket);
    }
    @RequestMapping("/getupdateticket")
    public TicketEntity getupdateticket(@RequestParam("ticketid") Integer ticketid){
        return ticketRepository.findOne(ticketid);
    }

    @RequestMapping("/assigncommenttoticket")
    public void assigncommenttoticket(@RequestParam("ticketid") Integer ticketid,
                                      @RequestParam("commentid") String commentid){
        TicketitemEntity item = new TicketitemEntity();
        item.setCommentid(commentid);
        item.setTicketid(ticketid);
        item.setMessageid("0");
        item.setPostid("0");
        ticketitemRepository.save(item);
    }

    @RequestMapping("/getrelatedticket")
    public List<TicketEntity> getrelatedticket(@RequestParam("commentid") String commentid){
        //Lay list attribute cua comment
        List<CommentattributeEntity> listattributeofcomment = commentattributeRepository.getAttributebyCommentID(commentid);
        List<TicketEntity> ticketlist = new ArrayList<TicketEntity>();
        //Khi comment da duoc danh tag
        if(listattributeofcomment!=null){
            for (CommentattributeEntity ca: listattributeofcomment) {
                //Voi moi attribute se lay 1 list<commentAttribuet> de lay commentid cua cac comment lien quan
                List<CommentattributeEntity> newlist = commentattributeRepository.findByAttID(ca.getAttributeid());
                //Voi moi comment lien quan
                if(newlist!=null){
                    for (CommentattributeEntity cae: newlist) {
                        //khong lay lai comment dang click
                        if(!cae.getCommentid().equals(commentid)){
                            //Voi moi commentid se kiem tra xem co thuoc ticket nao ko
                            TicketitemEntity item = ticketitemRepository.getTicketItemByCommentID(cae.getCommentid());
                            //khi comment da thuoc 1 ticket
                            if(item!=null){
                                //Lay ticket do ra them vao list
                                TicketEntity ticket = ticketRepository.findOne(item.getTicketid());
                                //Doan nay de tranh' trung` lap. ticket lay ra
                                if(ticket!=null){
                                    ticketlist.add(ticket);
                                }
                            }
                        }
                    }
                }
            }
        }
        return ticketlist;
    }

    @RequestMapping("/getrelatedcomment")
    public List<ExtendComments> getrelatedcomment(@RequestParam("commentid") String commentid){
        List<CommentattributeEntity> listattributeofcomment = commentattributeRepository.getAttributebyCommentID(commentid);
        List<ExtendComments> commentlist = new ArrayList<ExtendComments>();
        if(listattributeofcomment!=null){
            for (CommentattributeEntity ca: listattributeofcomment) {
                List<CommentattributeEntity> newlist = commentattributeRepository.findByAttID(ca.getAttributeid());
                for (CommentattributeEntity cae: newlist) {
                    if(!cae.getCommentid().equals(commentid)){
                        CommentEntity cmt = commentRepository.findOne(cae.getCommentid());

                        ExtendComments excmt = new ExtendComments();
                        excmt.setContent(cmt.getContent());
                        excmt.setCreatedAt(cmt.getCreatedAt());
                        excmt.setCreatedBy(cmt.getCreatedBy());
                        excmt.setCreatedByName(cmt.getCreatedByName());
                        excmt.setId(cmt.getId());
                        excmt.setPostId(cmt.getPostId());
                        excmt.setSentimentScore(cmt.getSentimentScore());
                        TicketitemEntity item = ticketitemRepository.getTicketItemByCommentID(cmt.getId());
                        if(item!=null){
                            excmt.setTicket(true);
                        }
                        commentlist.add(excmt);
                    }

                }
            }
        }
        return commentlist;
    }

    /*@RequestMapping("/forwardticket")
    public TicketrequestEntity forwardticket(@RequestParam("commentid") String commentid,
                                             @RequestParam("forwarduser") Integer forwarduser,
                                             @RequestParam("forwardnote") String forwardnote,
                                             HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        TicketEntity ticket =ticketRepository.findBycommentid(commentid);

        TicketrequestEntity ticketrequest = new TicketrequestEntity();
        ticketrequest.setRequestat(new Timestamp(new Date().getTime()));
        ticketrequest.setAssigner(user.getUserid());
        ticketrequest.setAssignee(forwarduser);
        ticketrequest.setNote(forwardnote);
        ticketrequest.setTicketid(ticket.getId());
        return ticketRequestRepository.save(ticketrequest);

    }*/

    /*@RequestMapping("/createticketforstaff")
    public TicketEntity createticketforstaff(HttpServletRequest request,
                                             @RequestParam("commentid") String commentid,
                                             @RequestParam("priority") Integer priority){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        if(session!=null){
            //Tao moi 1 ticket
            TicketEntity ticketEntity = new TicketEntity();
            ticketEntity.setCreatedby(user.getUserid());
//            ticketEntity.setCommentid(commentid);
            ticketEntity.setStatusid(1);
            ticketEntity.setAssignee(user.getUserid());
            ticketEntity.setActive(true);
            ticketEntity.setStatusid(2);
            ticketEntity.setPriority(priority);
            ticketEntity.setCreatedtime(new Timestamp(new Date().getTime()));

            TicketEntity ticket= ticketRepository.save(ticketEntity);

            //Tao 1 ticket history
            TicketstatuschangeEntity status = new TicketstatuschangeEntity();
            status.setTicketid(ticket.getId());
            status.setStatusid(2);
            status.setChangeby(user.getUserid());
            status.setAssignee(user.getUserid());
            status.setCreatedat(new Timestamp(new Date().getTime()));

            ticketStatusChangeRepository.save(status);
        }
        return null;
    }*/


}
