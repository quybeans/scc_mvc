package com.scc.ticketmanagement.controllers.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scc.ticketmanagement.Entities.SettingsSpiderEntity;
import com.scc.ticketmanagement.repositories.BrandRepository;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.repositories.SettingsSpiderRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.utilities.Constant;
import org.codehaus.groovy.util.ListHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by QuyBean on 11/17/2016.
 */

@RestController
public class AdminRESTController {

    @Autowired
    private SettingsSpiderRepository settingRepo;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrandRepository brandRepository;

    @RequestMapping("admin/overview")
    public List<Long>  countEverything() throws Exception
    {
        long brandCount = brandRepository.countAll();
        long countUser = userRepository.countUser();
        long countPage = pageRepository.countPage();
        List<Long> list = new ArrayList<>();
        list.add(countPage);list.add(brandCount);list.add(countUser);
        return list;
    }

    @RequestMapping("admin/getSettings")
    public List<SettingsSpiderEntity> getAllSettings(HttpServletRequest request) {
        try {
            if (request.getSession(false).getAttribute("username").equals("admin")) {
                List<SettingsSpiderEntity> listSettings = settingRepo.findAll();
                return listSettings;
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    @RequestMapping(value = "settings/save", method = RequestMethod.POST)
    public boolean saveSetting(@Param("txtToken") String txtToken, @Param("txtDeadTime") String txtDeadTime) {
        try {
            SettingsSpiderEntity token = new SettingsSpiderEntity();
            token.setName(Constant.CRAWLER_TOKEN_SETTINGS);
            if (txtToken.length() > 0) {
                token.setValue(txtToken);
                settingRepo.save(token);
            }

            SettingsSpiderEntity deadTime = new SettingsSpiderEntity();
            deadTime.setName(Constant.CRAWLER_TIME_LIMIT);
            if (txtDeadTime.length() > 0) {
                deadTime.setValue(txtDeadTime);
                settingRepo.save(deadTime);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
