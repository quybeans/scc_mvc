package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.TicketEntity;
import com.scc.ticketmanagement.services.TicketItemService;
import com.scc.ticketmanagement.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by QuyBean on 11/15/2016.
 */

@RestController
public class TicketRESTController {

    @Autowired
    private TicketItemService ticketItemService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping("comment/checkTicket")
    public TicketEntity checkCmtisTicketItem(String cmtID) {

        try {
            Integer rs = ticketItemService.getTicketItemByCommentId(cmtID).getTicketid();
            if (rs != null) {
                return ticketService.getTicketByID(rs);
            }
            return null;
        }catch (NullPointerException e)
        {
            System.out.println("Null ticketitem");
            return null;
        }
    }

}
