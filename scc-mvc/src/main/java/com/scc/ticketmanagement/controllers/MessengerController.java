package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Thien on 11/2/2016.
 */
@Controller
public class MessengerController {

    @Autowired
    PageService pageService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PageRepository pageRepository;

    @RequestMapping("/messengertest")
    public String messengertest() {
        return "/messenger/index";
    }

    @RequestMapping("/messenger")
    public String messenger(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session!=null) {
            String username = (String) session.getAttribute("username");
            int userid = userRepository.findUseridByUser(username);
            UserEntity user = userRepository.findOne(userid);
            List<PageEntity> pages = pageRepository.getAllPageByBrandId(user.getBrandid());
            model.addAttribute("pages",pages) ;
        }

        return "/messenger/index";
    }

    @RequestMapping("/messenger/index")
    public String messengerIndex() {
        return "/messenger/index";
    }


}
