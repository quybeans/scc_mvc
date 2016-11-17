package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.SettingsSpiderEntity;
import com.scc.ticketmanagement.repositories.SettingsSpiderRepository;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by QuyBean on 11/17/2016.
 */

@Controller
public class AdminController {

    @Autowired
    SettingsSpiderRepository settingRepo;


}
