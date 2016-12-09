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
    public TicketEntity checkCmtisTicketItem(HttpServletRequest request, String cmtID) {
        HttpSession session = request.getSession(false);
        try {
            Integer rs = ticketItemService.getTicketItemByCommentId(cmtID).getTicketid();
            if (rs != null) {
                TicketEntity tk = ticketService.getTicketByID(rs);
                String username = (String) session.getAttribute("username");
                int branid = userService.getUserByUsername(username).getBrandid();
                if (branid == tk.getBrandId())
                    return tk;
            }
            return null;
        } catch (NullPointerException e) {
            //e.printStackTrace();
            return null;
        }
    }
//
//    @RequestMapping("comment/addToTicket")
//    public Boolean checkCmtisTicketItem(int ticketId, String cmtId) {
//        if (ticketItemService.addCommentItemToTicket(ticketId, cmtId) != null) return true;
//        return false;
//    }

    @RequestMapping("comment/addToTicket")
    public Boolean checkCmtisTicketItem(@Param("ticketId") int ticketId, @Param("cmtId") String cmtId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        int userid = userService.getUserByUsername(username).getUserid();
        if (username != null) {
            if (ticketItemService.addCommentItemToTicket(ticketId, cmtId, userid) != null) return true;
        }
        return false;
    }

    @RequestMapping("comment/countUnhandle")
    public int checkUnhandle(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        String username = (String)session.getAttribute("username");
        int userid = userService.getUserByUsername(username).getUserid();
        if(username !=null)
        {
            return ticketService.countUnhandleTicket(userid);
        }
        return 0;
    }
}
