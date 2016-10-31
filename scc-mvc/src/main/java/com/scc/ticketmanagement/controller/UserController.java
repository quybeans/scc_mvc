package com.scc.ticketmanagement.controller;

import com.scc.ticketmanagement.entity.ProfileEntity;
import com.scc.ticketmanagement.entity.UserEntity;
import com.scc.ticketmanagement.extendEntity.User;
import com.scc.ticketmanagement.repository.ProfileRepository;
import com.scc.ticketmanagement.repository.UserRepository;
import com.scc.ticketmanagement.service.ProfileService;
import com.scc.ticketmanagement.service.UserService;
import com.scc.ticketmanagement.utility.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thien on 9/27/2016.
 */
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    UserService userService;

    @Autowired
    ProfileRepository profileRepository;

    @RequestMapping( value = "/getuser",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<UserEntity> admin(Model model) {
        List<UserEntity> entity = userService.findAll();
        return entity;
    }

    @RequestMapping( value = "/getalluser")
    @ResponseBody
    public List<User> getalluser(Model model) {
        List<UserEntity> entity = userService.findAll();
        List<User> userlist = new ArrayList<User>();
        for (UserEntity u: entity) {
            ProfileEntity p = profileRepository.findOne(u.getProfileid());
            User user = new User();
            user.setUserid(u.getUserid());
            user.setFirstname(p.getFirstname());
            user.setLastname(p.getLastname());
            userlist.add(user);
        }
        return userlist;
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


    @RequestMapping("/createuser")
    @ResponseBody
    public UserEntity create(@RequestParam("txtUsername") String username,
                             @RequestParam("txtPassword") String password,
                             @RequestParam("slRoleId") Integer roleId){
        ProfileEntity profileEntity= profileService.createProfile("","","","","","");
        UserEntity user = userService.createUser(username, password, roleId,profileEntity.getProfileid(),true);
        return user;
    }
    @RequestMapping("/Search")
    public String search(Model model, @RequestParam("txtSearch") String name) {
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
    @ResponseBody
    public String deactiveUser(@RequestParam("txtUserid") Integer userid){
        UserEntity user = userRepository.findOne(userid);
        //set user active = true khi active = false va nguoc lai
        user.setActive(!user.isActive());
        userRepository.save(user);
        return "success";
    }

    @RequestMapping("/GetUser")
    @ResponseBody
    public UserEntity getUser(@RequestParam("txtUserid") Integer userid){
        return userRepository.findOne(userid);
    }
}
