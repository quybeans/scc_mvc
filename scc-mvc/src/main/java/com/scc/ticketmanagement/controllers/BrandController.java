package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.BrandEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.BrandRepository;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by QuyBean on 11/10/2016.
 */

@Controller
public class BrandController {

    @Autowired
    private UserService userService;

    @Autowired
    private BrandRepository brandRepository;


    @RequestMapping("/brand/updateInfo")
    public String updateBrandInfo(HttpServletRequest request
            , @RequestParam("txtName") String name
            , @RequestParam("txtSologan") String sologan
            , @RequestParam("txtPhone") String phone
            , @RequestParam("txtMail") String mail
            , @RequestParam("txtAddress") String address
            , @RequestParam("txtLogo") String logo) throws DataAccessException{
        HttpSession session = request.getSession();
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                UserEntity user = userService.getUserByUsername(username);
                if (user.getRoleid() == Constant.ROLE_BRAND) {

                    BrandEntity brandEntity = brandRepository.findOne(user.getBrandid());
                    if (name.length() > 0) brandEntity.setName(name);
                    if (sologan.length() > 0) brandEntity.setSologan(sologan);
                    if (address.length() > 0) brandEntity.setAddress(address);
                    if (phone.length() > 0) brandEntity.setPhone(phone);
                    if (mail.length() > 0) brandEntity.setEmail(mail);
                    if (logo.length() > 0) brandEntity.setImage(logo);

                    brandRepository.save(brandEntity);
                    return "/brand/index";
                }
            }
        }
        return "/brand/index";
    }
}