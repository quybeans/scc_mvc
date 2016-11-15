package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.TicketEntity;
import com.scc.ticketmanagement.services.TicketItemService;
import com.scc.ticketmanagement.services.TicketService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by QuyBean on 11/15/2016.
 */

@RestController
public class TicketRESTController {

    @Autowired
    private TicketItemService ticketItemService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @RequestMapping("comment/checkTicket")
    public TicketEntity checkCmtisTicketItem(String cmtID) {

        try {
            Integer rs = ticketItemService.getTicketItemByCommentId(cmtID).getTicketid();
            if (rs != null) {
                return ticketService.getTicketByID(rs);
            }
            return null;
        } catch (NullPointerException e) {
//            System.out.println("Null ticketitem");
            return null;
        }
    }

    @RequestMapping("comment/addToTicket")
    public Boolean checkCmtisTicketItem(@Param("ticketId") int ticketId,@Param("cmtId") String cmtId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        int userid = userService.getUserByUsername(username).getUserid();
        if (username != null) {
            if (ticketItemService.addCommentItemToTicket(ticketId, cmtId,userid) != null) return true;
        }
        return false;
    }

}
