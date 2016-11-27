package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Thien on 11/2/2016.
 */
@Controller
public class MessengerController {

    String returnUrl = "redirect:/login";
    @Autowired
    PageService pageService;

    @Autowired
    UserService userService;

    @RequestMapping("/messengertest")
    public String messengertest() {
        return "/messenger/messengertest";
    }

    @RequestMapping("/messenger")
    public String messenger(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session!=null) {
            String username = (String) session.getAttribute("username");
            if (username!=null){
                int userid = userService.getUserByUsername(username).getUserid();
                UserEntity user = userService.getUserByID(userid);
                List<PageEntity> pages = pageService.getAllActivePageByBrandId(user.getBrandid());
                model.addAttribute("pages",pages) ;
                returnUrl = "/messenger/index";
            }

        }

        return returnUrl;
    }

    @RequestMapping("/messenger/ticket")
    public String messengerTicket(Model model, HttpServletRequest request,
                                  @RequestParam("senderid") String senderId,
                                  @RequestParam("receiverid") String receiverId,
                                  @RequestParam("messageid") String messageId) {
        HttpSession session = request.getSession(false);

        if (session!=null) {
            String username = (String) session.getAttribute("username");
            int userid = userService.getUserByUsername(username).getUserid();
            UserEntity user = userService.getUserByID(userid);
            if (username!=null){
                List<PageEntity> pages = pageService.getAllActivePageByBrandId(user.getBrandid());
                model.addAttribute("pages",pages) ;
                model.addAttribute("senderId", senderId);
                model.addAttribute("receiverid", receiverId);
                model.addAttribute("messageid", messageId);
                returnUrl = "/messenger/index";
            }

        }

        return returnUrl;
    }

    @RequestMapping("/messenger/index")
    public String messengerIndex() {
        return "/messenger/index";
    }


}
