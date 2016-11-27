package com.scc.ticketmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by QuyBean on 11/9/2016.
 */

@Controller
public class NavigationController {

    @RequestMapping("/brand/index")
    public String brandInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return "brand/index";
    }

    @RequestMapping("/brand/report")
    public String brandReport(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return "/brand/report";
    }

    @RequestMapping("/customercare")
    public String customercare(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String returnUrl = "redirect:/login";
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                returnUrl = "customercare";
            }
        }
        return returnUrl;
    }

    @RequestMapping("/admin/index")
    public String navAdminPage() {
        return "admin/index";
    }


}
