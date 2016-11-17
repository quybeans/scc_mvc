package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.SettingsSpiderEntity;
import com.scc.ticketmanagement.repositories.SettingsSpiderRepository;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by QuyBean on 11/17/2016.
 */

@RestController
public class AdminRESTController {

    @Autowired
    private SettingsSpiderRepository settingRepo;


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
