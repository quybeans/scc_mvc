package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.Entities.TicketitemEntity;
import com.scc.ticketmanagement.repositories.CommentRepository;
import com.scc.ticketmanagement.repositories.TicketitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by user on 11/2/2016.
 */
@RestController
public class TicketItemController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private TicketitemRepository ticketitemRepository;

    @RequestMapping("/loadticketitem")
   public List<CommentEntity> loadticketitem(@RequestParam("ticketid") Integer ticketid){
        List<CommentEntity> listcmt = new ArrayList<CommentEntity>();
        List<TicketitemEntity> listitem = ticketitemRepository.getTicketItemByTicketID(ticketid);
        for (TicketitemEntity ti: listitem) {
            if(!ti.getCommentid().equals("0")){
                CommentEntity cmt= commentRepository.findOne(ti.getCommentid());

                listcmt.add(cmt);
            }
        }

        Collections.sort(listcmt, new Comparator<CommentEntity>() {
            @Override
            public int compare(CommentEntity o1, CommentEntity o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        return listcmt;
    }

}
