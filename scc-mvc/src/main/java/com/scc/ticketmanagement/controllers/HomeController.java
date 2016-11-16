package com.scc.ticketmanagement.controllers;


import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.TicketEntity;
import com.scc.ticketmanagement.Entities.TicketstatuschangeEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.BrandRepository;
import com.scc.ticketmanagement.repositories.TicketRepository;
import com.scc.ticketmanagement.repositories.TicketStatusChangeRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.FacebookaccountService;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Thien on 9/23/2016.
 */
@Controller
public class HomeController {
    //    private ProfileService profileService;
    @Autowired
    TicketStatusChangeRepository ticketStatusChangeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    UserService userService;
    @Autowired
    FacebookaccountService fbService;

    @Autowired
    private BrandRepository brandRepository;

    @RequestMapping("/testadmin")
    public String test() {
        return "Admins";
    }

    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    @RequestMapping("/Head")
    public String head() {
        return "Head";
    }

    @RequestMapping("/SideBar")
    public String sidebar() {
        return "SideBar";
    }

    @RequestMapping("/MasterPage")
    public String masterpage() {
        return "MasterPage";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }

    @RequestMapping("/manageticket")
    public String manageticket() {
        return "manageticket";
    }

//    @RequestMapping("/doLogin")
//    public String doLogin(Model model,
//                          HttpSession session,
//                          @RequestParam("txtUsername") String username,
//                          @RequestParam("txtPassword") String password) {
//
//        UserEntity user = userService.getUserByUsername(username, password);
//        if (user == null) {
//            model.addAttribute("error", "Incorrect username or password");
//            return "login";
//        }
//
//        session.setAttribute("username", user.getUsername());
//
//        if (user.getProfileid() != null) {
//            ProfileEntity profileEntity = profileService.getProfileByID(user.getProfileid());
//
//            session.setAttribute("fullname", profileEntity.getFirstname() + " " + profileEntity.getLastname());
//
//
//        }
//        if (user.getRoleid() == Constant.ROLE_ADMIN) {
//            return "redirect:admin";
//        } else if (user.getRoleid() == Constant.ROLE_STAFF) {
//            return "redirect:customercare";
//        } else if (user.getRoleid() == Constant.ROLE_SUPERVISOR) {
//            return "redirect:Report";
//        }
//        return "404";
//    }

    @RequestMapping("/doLogin")
    public String loginacc(HttpSession session
            , @RequestParam("txtUsername") String username, @RequestParam("txtPassword") String password) {
        UserEntity userEntity = userService.findUser(username, password);
        if (userEntity == null) {
            return "login";
        }

        session.setAttribute("username", userEntity.getUsername());


        ProfileEntity profileEntity = profileService.getProfileByID(userEntity.getUserid());

        session.setAttribute("fullname", profileEntity.getFirstname() + " " + profileEntity.getLastname());


        if (userEntity.getRoleid() == Constant.ROLE_ADMIN) {
            return "redirect:admin";
        } else if (userEntity.getRoleid() == Constant.ROLE_STAFF) {
            return "CustomerCare";
        } else if (userEntity.getRoleid() == Constant.ROLE_SUPERVISOR) {
            return "manageticket";
        } else if (userEntity.getRoleid() == Constant.ROLE_BRAND) {
            return "manageticket";
        }
        return "404";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String username = (String) session.getAttribute("username");

            UserEntity user = userService.getUserByUsername(username);
            if (user != null) {
                switch (user.getRoleid()) {
                    case Constant.ROLE_ADMIN:
                        return "redirect:admin";
                    case Constant.ROLE_STAFF:
                        return "redirect:customercare";
                    case Constant.ROLE_SUPERVISOR:
                        return "Report";
                }//end switch
            }//end if user
        }//end if session
        return "login";
    }

    @RequestMapping("/LogOut")
    public String LogOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "login";
    }


    @RequestMapping("/engagement")
    public String home() {
        return "engagement";
    }

    @RequestMapping("/customercare")
    public String customercare() {
        return "customercare";
    }

    @RequestMapping("/getcurrentticket")
    public String getcurrentticket(@RequestParam("ticketid") Integer ticketid) {

        return "customercare";
    }

    @RequestMapping("/followticket")
    public String followticket(@RequestParam("ticketid") Integer ticketid, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        TicketEntity ticket = ticketRepository.findOne(ticketid);
        if(ticket.getStatusid()==Constant.STATUS_ASSIGN){
            if(ticket.getAssignee()==user.getUserid()){
                ticket.setStatusid(Constant.STATUS_INPROCESS);
                ticket.setNote("Start follow ticket");
                ticketRepository.save(ticket);

                TicketstatuschangeEntity change = new TicketstatuschangeEntity();
                change.setChangeby(user.getUserid());
                change.setCreatedat(new Timestamp(new Date().getTime()));
                change.setStatusid(Constant.STATUS_INPROCESS);
                change.setTicketid(ticketid);
                change.setNote("Start follow ticket");
                ticketStatusChangeRepository.save(change);
            }

        }

        model.addAttribute("ticketid", ticketid);
        return "followticket";
    }


}
