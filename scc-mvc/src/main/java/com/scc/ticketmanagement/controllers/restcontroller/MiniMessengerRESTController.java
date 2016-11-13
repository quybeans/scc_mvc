package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.MessageEntity;
import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.exentities.PageConversation;
import com.scc.ticketmanagement.services.MessageService;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thien on 11/13/2016.
 */
@RestController
public class MiniMessengerRESTController {

    @Autowired
    MessageService messageService;

    @Autowired
    PageService pageService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/messenger/getAllActivePageUnreadMessage", method = RequestMethod.GET)
    public List<PageConversation> getAllActivePage(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        List<PageConversation> result = new ArrayList<>();
        if (session != null) {

            int brandId = this.getCurrentUserBrandId(session);
            PageConversation pConversation = null;

            List<PageEntity> pages = pageService.getPagesByBrandId(brandId);
            for (PageEntity page : pages) {
                pConversation = new PageConversation();
                pConversation.setPageid(page.getPageid());
                pConversation.setName(page.getName());
                pConversation.setUnreadMessage(messageService.getNumberOfUnreadMessageInPage(page.getPageid()));
                result.add(pConversation);
            }
        }
        return result;

    }

    private int getCurrentUserBrandId(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return 0;
        }
        return userService.getBrandIdByUsername(username);
    }
}
