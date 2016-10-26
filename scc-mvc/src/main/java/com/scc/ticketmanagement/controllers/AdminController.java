package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Thien on 9/27/2016.
 */
@Controller
public class AdminController {
    @Autowired
    ProfileService profileService;

    @Autowired
    UserService userService;

    @RequestMapping("/admin")
    public String admin(Model model) {
        List<UserEntity> entity = userService.findAll();
        model.addAttribute("entity", entity);
        return "Admin";
    }

    @RequestMapping("/delete")
    public String deleteUser(@RequestParam("userid") Integer id) {

        userService.Delete(id);
        return "redirect:admin";
    }

    @RequestMapping("/Update")
    public String updateUser(@RequestParam("txtId") Integer id,
                             @RequestParam("txtUsername") String username,
                             @RequestParam("txtPassword") String password,
                             @RequestParam("slRoleId") Integer roleId) {

        userService.updateUser(id, username, password, roleId);
        return "redirect:admin";
    }

    @RequestMapping("/Create")
    public String createUser(@RequestParam("txtUsername") String username,
                             @RequestParam("txtPassword") String password,
                             @RequestParam("slRoleId") Integer roleId ) {
       // profileService.createProfile("","","","","","");
        userService.createUser(username, password, roleId, true);
        return "redirect:admin";
    }

    @RequestMapping("/Search")
    public String search(Model model,@RequestParam("txtSearch") String name) {
        List<UserEntity> lst= userService.searchUser(name);
        model.addAttribute("entity", lst);
        return "Admin";
    }

    @RequestMapping("GetStaffs")
    public String getStaffs(Model model) {
        List<UserEntity> lst= userService.getUsersByRole(Constant.ROLE_STAFF);

        model.addAttribute("entity", lst);
        return "Admin";
    }

    @RequestMapping("GetAdmins")
    public String getAdmins(Model model) {
        List<UserEntity> lst= userService.getUsersByRole(Constant.ROLE_ADMIN);

        model.addAttribute("entity", lst);
        return "Admin";
    }

    @RequestMapping("GetSupervisors")
    public String getSupervisors(Model model) {
        List<UserEntity> supervisorList  = userService.getUsersByRole(Constant.ROLE_SUPERVISOR);
        model.addAttribute("entity", supervisorList);
        return "Admin";
    }

    @RequestMapping("/ChangeActive")
    public String deactiveUser(@RequestParam("txtUserid") Integer userid,
                               @RequestParam("btnActive") String btnActive){
        if(btnActive.equals("Deactive")){
            System.out.println("DEACTIVE");
            userService.changeActive(userid,false);
        }else {
            System.out.println("ACTIVE");
            userService.changeActive(userid,true);
        }
        return "redirect:admin";
    }
}
