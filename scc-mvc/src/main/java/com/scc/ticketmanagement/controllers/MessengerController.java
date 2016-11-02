package com.scc.ticketmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Thien on 11/2/2016.
 */
@Controller
public class MessengerController {

    @RequestMapping("/messengertest")
    public String messengertest() {
        return "/messenger/index";
    }

    @RequestMapping("/messenger")
    public String messenger() {
        return "/messenger/index";
    }
    @RequestMapping("/messenger/index")
    public String messengerIndex() {
        return "/messenger/index";
    }

}
