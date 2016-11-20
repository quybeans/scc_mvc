package com.scc.ticketmanagement.controllers;


import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.exentities.*;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.utilities.Constant;
import com.scc.ticketmanagement.utilities.FilterCriteria;
import com.scc.ticketmanagement.utilities.TicketSpecification;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
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
            ticketEntity.setAssignee(assignee);
            ticketEntity.setActive(true);
            ticketEntity.setStatusid(Constant.STATUS_ASSIGN);
            ticketEntity.setPriority(priority);
            ticketEntity.setCreatedtime(new Timestamp(new Date().getTime()));
            ticketEntity.setNote(note);
            ticketEntity.setDuetime(new Timestamp(new Date().getTime()));
            ticketEntity.setBrandId(user.getBrandid());

            TicketEntity ticket= ticketRepository.save(ticketEntity);
            //Tao 1 ticket history
            TicketstatuschangeEntity status = new TicketstatuschangeEntity();
            status.setTicketid(ticket.getId());
            status.setStatusid(Constant.STATUS_CREATE);
            status.setChangeby(user.getUserid());
            status.setAssignee(assignee);
            status.setCreatedat(new Timestamp(new Date().getTime()));
            status.setNote(note);
            ticketStatusChangeRepository.save(status);
            return ticket;
        }
        return null;
    }

    @RequestMapping("/updateexpiredticket")
    public String updateexpiredticket(@RequestParam("ticketid") Integer ticketid,
                                    @RequestParam("duration") Integer duration){
        List<PriorityEntity> priority = priorityReposioty.getNextPriority(duration);
        TicketEntity ticket = ticketRepository.findOne(ticketid);
        if(priority.size()>0){
            ticket.setPriority(priority.get(0).getId());
            ticket.setDuetime(new Timestamp(new Date().getTime()));
            ticket.setNote("This ticket is expired,System change priority to "+priority.get(0).getName());
            ticketRepository.save(ticket);

            TicketstatuschangeEntity change = new TicketstatuschangeEntity();
            change.setNote("This ticket is expired");
            change.setPriorityid(priority.get(0).getId());
            change.setTicketid(ticketid);
            change.setCreatedat(new Timestamp(new Date().getTime()));
            change.setChangeby(0);
            ticketStatusChangeRepository.save(change);
            return "This ticket is expired,System change priority to "+priority.get(0).getName();
        }else{
            ticket.setStatusid(Constant.STATUS_EXPIRED);
            ticket.setNote("This ticket is expired");
            ticketRepository.save(ticket);

            TicketstatuschangeEntity changes = new TicketstatuschangeEntity();
            changes.setNote(" ");
            changes.setAssignee(ticket.getCreatedby());
            changes.setStatusid(Constant.STATUS_EXPIRED);
            changes.setTicketid(ticketid);
            changes.setChangeby(0);
            changes.setCreatedat(new Timestamp(new Date().getTime()));
            ticketStatusChangeRepository.save(changes);
            return "This ticket is expired";
        }

    }


    @RequestMapping(value = "/getallticket",method = RequestMethod.GET,produces = "application/json")
    public List<ExtendTicket> getallticket(HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        List<TicketEntity> listticket = new ArrayList<TicketEntity>();
        if(user.getRoleid()!=Constant.ROLE_STAFF){
            listticket = ticketRepository.getTicketOrderByPriority(user.getBrandid());
        }else {
            listticket=ticketRepository.getStaffTicketOrderByPriority(user.getUserid(),user.getBrandid());
        }


       return getExtendTicketList(listticket);
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
        if(!assignnote.equals(" ")){
            ticket.setNote(assignnote);
        }
        ticket.setStatusid(Constant.STATUS_ASSIGN);
        ticket.setDuetime(new Timestamp(new Date().getTime()));

        //Luu 1 TicketHistory
        TicketstatuschangeEntity status = new TicketstatuschangeEntity();
        status.setTicketid(ticket.getId());
        status.setStatusid(Constant.STATUS_ASSIGN);
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
        if(!statusnote.equals(" ")){
            ticket.setNote(statusnote);
        }



        //Them 1 ticket history
        TicketstatuschangeEntity statuschange = new TicketstatuschangeEntity();
        statuschange.setTicketid(ticketid);
        statuschange.setChangeby(user.getUserid());
        statuschange.setStatusid(status);
        statuschange.setCreatedat(new Timestamp(new Date().getTime()));
        statuschange.setNote(statusnote);
        if(status==Constant.STATUS_ASSIGN){
            ticket.setDuetime(new Timestamp(new Date().getTime()));
            statuschange.setAssignee(ticket.getAssignee());
        }else{

        }
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
                profileEntity = profileRepository.findOne(ticket.getCreatedby());
                detail.setCreateby(profileEntity.getFirstname()+ " " + profileEntity.getLastname());
            }

            //Get Full name cua nguoi duoc assign ticket
            if(ticket.getAssignee()!=null){
                profileEntity= profileRepository.findOne(userRepository.findOne(ticket.getAssignee()).getUserid());

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
                case Constant.STATUS_ASSIGN: detail.setStatusid("Assigned"); break;
                case Constant.STATUS_INPROCESS: detail.setStatusid("Inprocess"); break;
                case Constant.STATUS_CLOSE: detail.setStatusid("Close"); break;
                case Constant.STATUS_SOLVED: detail.setStatusid("Solved"); break;
                case Constant.STATUS_EXPIRED: detail.setStatusid("Expired"); break;
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

        ProfileEntity profileEntity = profileRepository.findOne(userRepository.findOne(ticket.getAssignee()).getUserid());
        extendTicket.setAssigneeuser(profileEntity.getFirstname());

        profileEntity = profileRepository.findOne(userRepository.findOne(ticket.getCreatedby()).getUserid());
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
                        pro = profileRepository.findOne(tk.getChangeby());
                        th.setUserid(pro.getFirstname() + " " + pro.getLastname());
                    }


                    //Get Full name cua nguoi duoc assign
                    if(tk.getAssignee()!=null){
                        pro=profileRepository.findOne(tk.getAssignee());
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
                                     @RequestParam("ticketpriority") Integer ticketpriority,
                                     HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        TicketEntity ticket = ticketRepository.findOne(ticketid);
        if(!ticketnote.equals(" ")){
            ticket.setNote(ticketnote);
        }

        ticket.setPriority(ticketpriority);
//        ticket.setDuetime(new Timestamp(new Date().getTime()));

        TicketstatuschangeEntity change = new TicketstatuschangeEntity();
        change.setTicketid(ticketid);
        change.setChangeby(user.getUserid());
        change.setPriorityid(ticketpriority);
        change.setNote(ticketnote);
        change.setCreatedat(new Timestamp(new Date().getTime()));
        ticketStatusChangeRepository.save(change);
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
        item.setMessageid(0);
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

    @RequestMapping("/forwardticket")
    public TicketrequestEntity forwardticket(@RequestParam("ticketid") Integer ticketid,
                                             @RequestParam("forwarduser") Integer forwarduser,
                                             @RequestParam("forwardnote") String forwardnote,
                                             HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        TicketEntity ticket =ticketRepository.findOne(ticketid);

        TicketrequestEntity ticketrequest = new TicketrequestEntity();
        ticketrequest.setRequestat(new Timestamp(new Date().getTime()));
        ticketrequest.setAssigner(user.getUserid());
        ticketrequest.setAssignee(forwarduser);
        ticketrequest.setNote(forwardnote);
        ticketrequest.setTicketid(ticket.getId());
        return ticketRequestRepository.save(ticketrequest);

    }

    @RequestMapping("/createticketforstaff")
    public TicketEntity createticketforstaff(HttpServletRequest request,
                                             @RequestParam("ticketname") String ticketname,
                                             @RequestParam("ticketpriority") Integer ticketpriority,
                                             @RequestParam("ticketnote") String ticketnote){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        if(session!=null){
            //Tao moi 1 ticket
            TicketEntity ticketEntity = new TicketEntity();
            ticketEntity.setCreatedby(user.getUserid());
            ticketEntity.setStatusid(Constant.STATUS_ASSIGN);
            ticketEntity.setAssignee(user.getUserid());
            ticketEntity.setName(ticketname);
            ticketEntity.setNote(ticketnote);
            ticketEntity.setActive(true);
            ticketEntity.setPriority(ticketpriority);
            ticketEntity.setCreatedtime(new Timestamp(new Date().getTime()));
            ticketEntity.setDuetime(new Timestamp(new Date().getTime()));
            ticketEntity.setBrandId(user.getBrandid());
            TicketEntity ticket= ticketRepository.save(ticketEntity);

            //Tao 1 ticket history
            TicketstatuschangeEntity status = new TicketstatuschangeEntity();
            status.setTicketid(ticket.getId());
            status.setStatusid(Constant.STATUS_CREATE);
            status.setChangeby(user.getUserid());
            status.setAssignee(user.getUserid());
            status.setCreatedat(new Timestamp(new Date().getTime()));
            status.setNote(ticketnote);

            ticketStatusChangeRepository.save(status);
            return ticket;
        }
        return null;
    }

    @RequestMapping("/reopenticket")
    public TicketEntity reopenticket(@RequestParam("ticketid") Integer ticketid,
                                           HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        //Thay doi status trong ticket
        TicketEntity ticket=ticketRepository.findOne(ticketid);
        ticket.setStatusid(Constant.STATUS_ASSIGN);

        ProfileEntity profile = profileRepository.findOne(user.getUserid());
        ticket.setNote(profile.getFirstname() +" "+ profile.getLastname() + " Reopen this ticket ");


        //Them 1 ticket history
        TicketstatuschangeEntity statuschange = new TicketstatuschangeEntity();
        statuschange.setTicketid(ticketid);
        statuschange.setChangeby(user.getUserid());
        statuschange.setStatusid(Constant.STATUS_REOPEN);
        statuschange.setCreatedat(new Timestamp(new Date().getTime()));
        statuschange.setNote(" ");
        ticketStatusChangeRepository.save(statuschange);
        return ticketRepository.save(ticket);
    }

    @RequestMapping("/sortbytime")
    public List<ExtendTicket> sortbytime(HttpServletRequest request){

       List<ExtendTicket> ticketlist= getallticket(request);
        Collections.sort(ticketlist, new Comparator<ExtendTicket>() {
            @Override
            public int compare(ExtendTicket o1, ExtendTicket o2) {
                return o2.getCreatedtime().compareTo(o1.getCreatedtime());
            }
        });
        return ticketlist;
    }

    @RequestMapping("/sortbystatus")
    public List<ExtendTicket> sortbystatus(HttpServletRequest request){

        List<ExtendTicket> ticketlist= getallticket(request);
        Collections.sort(ticketlist, new Comparator<ExtendTicket>() {
            @Override
            public int compare(ExtendTicket o1, ExtendTicket o2) {
                return o1.getStatusid().compareTo(o2.getStatusid());
            }
        });
        return ticketlist;
    }

    @RequestMapping("/filterticket")
    public List<ExtendTicket> filterticket(@RequestParam("status") Integer status,
                             @RequestParam("priority") Integer priority,
                             @RequestParam("createdby") Integer createdby,
                             @RequestParam("assignee") Integer assignee){
        System.out.println("status: " +status+" priority: " + priority+" created by:" +createdby+" assignee: "+assignee );
        TicketSpecification spec = new TicketSpecification(new FilterCriteria("statusid","%",status));
        TicketSpecification spec1 = new TicketSpecification(new FilterCriteria("priority","%",priority));
        TicketSpecification spec2 = new TicketSpecification(new FilterCriteria("createdby","%",createdby));
        TicketSpecification spec3 = new TicketSpecification(new FilterCriteria("assignee","%",assignee));
        if(status!=null) {
            spec = new TicketSpecification(new FilterCriteria("statusid", ":", status));
        }
        if(priority!=null) {
            spec1 = new TicketSpecification(new FilterCriteria("priority", ":", priority));
        }
        if(createdby!=null){
            spec2 = new TicketSpecification(new FilterCriteria("createdby",":",createdby));
        }
        if(assignee!=null){
            spec3 = new TicketSpecification(new FilterCriteria("assignee",":",assignee));
        }


        List<TicketEntity> listticket = ticketRepository.findAll(Specifications.where(spec).and(spec1).and(spec2).and(spec3));


        return getExtendTicketList(listticket);
    }

    private List<ExtendTicket> getExtendTicketList(List<TicketEntity> ticketlist){
        List<ExtendTicket> listextendticket = new ArrayList<ExtendTicket>();
        for (TicketEntity tk: ticketlist) {
            ExtendTicket extendticket = new ExtendTicket();
            extendticket.setCountitem(ticketitemRepository.counTicketItem(tk.getId()));
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
            ProfileEntity createTicketProfile = profileRepository.findOne(createTicketUser.getUserid());
            //Get Fullname cua ng ta ticket de set vao extendTicket
            extendticket.setCreatebyuser(createTicketProfile.getFirstname() + " " +createTicketProfile.getLastname());

            //Get UserEntity cua ng duoc assign  ticket
            UserEntity assigneeUser =userRepository.findOne(extendticket.getAssignee());
            //Get ProfileEntity cua ng duoc assign  ticket
            ProfileEntity assigneeTicketProfile = profileRepository.findOne(assigneeUser.getUserid());
            //Get Fullname cua ng ta ticket de set vao extendTicket
            extendticket.setAssigneeuser(assigneeTicketProfile.getFirstname() + " " + assigneeTicketProfile.getLastname());
            switch (assigneeUser.getRoleid()){
                case Constant.ROLE_ADMIN: extendticket.setAssigneerole("Admin"); break;
                case Constant.ROLE_BRAND: extendticket.setAssigneerole("Brand Manager"); break;
                case Constant.ROLE_STAFF: extendticket.setAssigneerole("Staff"); break;
                case Constant.ROLE_SUPERVISOR: extendticket.setAssigneerole("Supervisor"); break;
            }

            switch (extendticket.getStatusid()){
                case Constant.STATUS_ASSIGN: extendticket.setCurrentstatus("Assigned"); break;
                case Constant.STATUS_INPROCESS: extendticket.setCurrentstatus("Inprocess"); break;
                case Constant.STATUS_CLOSE: extendticket.setCurrentstatus("Close"); break;
                case Constant.STATUS_SOLVED: extendticket.setCurrentstatus("Solved"); break;
                case Constant.STATUS_EXPIRED: extendticket.setCurrentstatus("Expired"); break;
            }

            //lay ten priority cua ticket
            PriorityEntity priority = priorityReposioty.findOne(tk.getPriority());
            extendticket.setCurrentpriority(priority.getName());
            listextendticket.add(extendticket);

        }
        return listextendticket;
    }

    @RequestMapping("/deleteticket")
    public void deleteticket(@RequestParam("ticketid") Integer ticketid){
        TicketEntity ticket = ticketRepository.findOne(ticketid);
        ticketRepository.delete(ticket);
    }
}
