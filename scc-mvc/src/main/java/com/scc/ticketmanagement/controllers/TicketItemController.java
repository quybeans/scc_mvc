//package com.scc.ticketmanagement.controllers;
//
//import com.scc.ticketmanagement.Entities.CommentEntity;
//import com.scc.ticketmanagement.Entities.TicketitemEntity;
//import com.scc.ticketmanagement.Entities.TicketstatuschangeEntity;
//import com.scc.ticketmanagement.exentities.TicketItemAndHistory;
//import com.scc.ticketmanagement.repositories.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
///**
// * Created by user on 11/2/2016.
// */
//@RestController
//public class TicketItemController {
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ProfileRepository profileRepository;
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Autowired
//    private TicketitemRepository ticketitemRepository;
//
//    @Autowired
//    private TicketStatusChangeRepository ticketStatusChangeRepository;
//
//    @RequestMapping("/loadticketitem")
//   public List<TicketItemAndHistory> loadticketitem(@RequestParam("ticketid") Integer ticketid){
//        List<CommentEntity> listcmt = new ArrayList<CommentEntity>();
//        List<TicketitemEntity> listitem = ticketitemRepository.getTicketItemByTicketID(ticketid);
//        List<TicketstatuschangeEntity> listStatusChange=  ticketStatusChangeRepository.getTicketChanges(ticketid);
//        List<TicketItemAndHistory> customizelist = new ArrayList<TicketItemAndHistory>();
//        for (TicketitemEntity ti: listitem) {
//            if(!ti.getCommentid().equals("0")){
//                CommentEntity cmt= commentRepository.findOne(ti.getCommentid());
//                TicketItemAndHistory tiah = new TicketItemAndHistory();
//                tiah.setContent(cmt.getContent());
//                tiah.setCreatedAt(ti.getCreatedAt());
//                tiah.setCreatedBy(cmt.getCreatedBy());
//                tiah.setCreatedByName(cmt.getCreatedByName());
//                tiah.setSentimentScore(cmt.getSentimentScore());
//                tiah.setId(cmt.getId());
//                tiah.setPostId(cmt.getPostId());
//                tiah.setCommentat(cmt.getCreatedAt());
//               tiah.setItem(true);
//                customizelist.add(tiah);
//            }
//        }
//        for (TicketstatuschangeEntity change:listStatusChange) {
//            TicketItemAndHistory tiah = new TicketItemAndHistory();
//            tiah.setTicketid(change.getTicketid());
//            ProfileEntity profile = new ProfileEntity();
//
//           if(change.getAssignee()!=null){
//                profile = profileRepository.findOne(userRepository.findOne(change.getAssignee()).getProfileid());
//               tiah.setAssignee(profile.getFirstname()+ " " + profile.getLastname());
//           }
//
//
//            profile = profileRepository.findOne(userRepository.findOne(change.getChangeby()).getProfileid());
//            tiah.setChangeby(profile.getFirstname()+ " " + profile.getLastname());
//            tiah.setStatusid(change.getStatusid());
//            tiah.setCreatedAt(change.getCreatedat());
//            tiah.setNote(change.getNote());
//            customizelist.add(tiah);
//        }
//
//
//        Collections.sort(customizelist, new Comparator<CommentEntity>() {
//            @Override
//            public int compare(CommentEntity o1, CommentEntity o2) {
//                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
//            }
//        });
//        for (TicketItemAndHistory tiah: customizelist) {
//            System.out.println("Create At:" + tiah.getCreatedAt());
//        }
//
//        return customizelist;
//    }
//
//}
