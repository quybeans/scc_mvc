package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.exentities.ExtendTicketItem;
import com.scc.ticketmanagement.exentities.TicketHistory;
import com.scc.ticketmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by user on 11/2/2016.
 */
@RestController
public class TicketItemController {
    @Autowired
    MessageitemRepository messageitemRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    PriorityReposioty priorityReposioty;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private TicketitemRepository ticketitemRepository;

    @Autowired
    private TicketStatusChangeRepository ticketStatusChangeRepository;

    @RequestMapping("/loadticketitem")
   public List<ExtendTicketItem> loadticketitem(@RequestParam("ticketid") Integer ticketid){

        List<TicketitemEntity> listitem = ticketitemRepository.getTicketItemByTicketID(ticketid);
        List<TicketstatuschangeEntity> listStatusChange=  ticketStatusChangeRepository.getTicketChanges(ticketid);
        List<ExtendTicketItem> customizelist = new ArrayList<ExtendTicketItem>();
        if(listitem!=null){
            for (TicketitemEntity ti: listitem) {
                if(!ti.getCommentid().equals("0")){
                    CommentEntity cmt= commentRepository.findOne(ti.getCommentid());
                    ExtendTicketItem item = new ExtendTicketItem();
                    item.setComment(cmt);
                    item.setCreatedAt(ti.getCreatedAt());
                    customizelist.add(item);
                }

                if(ti.getMessageid()!=0){
                    MessageitemEntity messageitem = messageitemRepository.findOne(ti.getMessageid());
                    MessageEntity message = messageRepository.findOne(messageitem.getMessageIdStart());
                    ExtendTicketItem item = new ExtendTicketItem();
                    item.setMessage(message);
                    customizelist.add(item);
                }

                if(!ti.getPostid().equals("0")){

                }
            }
        }

        if(listStatusChange!=null){
            for (TicketstatuschangeEntity change:listStatusChange) {
                ExtendTicketItem item = new ExtendTicketItem();
                item.setTicketid(change.getTicketid());
                ProfileEntity profile = new ProfileEntity();
                TicketHistory history = new TicketHistory();
                if(change.getAssignee()!=0){
                    profile = profileRepository.findOne(userRepository.findOne(change.getAssignee()).getProfileid());
                    history.setAssignee(profile.getFirstname()+ " " + profile.getLastname());
                }

                if(change.getChangeby()==0){
                    history.setUserid("System");
                }else{
                    profile = profileRepository.findOne(userRepository.findOne(change.getChangeby()).getProfileid());
                    history.setUserid(profile.getFirstname()+ " " + profile.getLastname());
                }



                if(change.getStatusid()!=0){
                    history.setStatusid(change.getStatusid());
                }
                history.setCreatedat(change.getCreatedat());
                history.setNote(change.getNote());
                if(change.getPriorityid()!=0){
                    PriorityEntity priority = priorityReposioty.findOne(change.getPriorityid());
                    history.setPriority(priority.getName());
                }
                item.setHistory(history);
                item.setCreatedAt(history.getCreatedat());
                customizelist.add(item);
            }
        }



        Collections.sort(customizelist, new Comparator<ExtendTicketItem>() {
            @Override
            public int compare(ExtendTicketItem o1, ExtendTicketItem o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });

        Collections.reverse(customizelist);

        return customizelist;
    }

    @RequestMapping("/addmessagetoitem")
    public void addmessagetoitem(@RequestParam("ticketid") Integer ticketid,
                                 @RequestParam("messageid") String messageid){

    }

}
