package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.exentities.User;
import com.scc.ticketmanagement.repositories.ProfileRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.Constant;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "/getuser", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<UserEntity> admin(Model model) {
        List<UserEntity> entity = userService.findAll();
        return entity;
    }

    @RequestMapping(value = "/getalluser")
    @ResponseBody
    public List<User> getalluser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity loginuser = userRepository.findUserByUsername(loginUser);

        List<UserEntity> entity = userRepository.getAllUserInBrand(loginuser.getBrandid());
        List<User> userlist = new ArrayList<User>();
        for (UserEntity u : entity) {
            ProfileEntity p = profileRepository.findOne(u.getUserid());
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
                             @RequestParam("slRoleId") Integer roleId) {
        ProfileEntity profileEntity = profileService.createProfile("", "", "", 1, "", "");
        UserEntity user = userService.createUser(username, password, roleId, profileEntity.getUserid(), true);
        return user;
    }

    @RequestMapping("/Search")
    public String search(Model model, @RequestParam("txtSearch") String name) {
        List<UserEntity> lst = userService.searchUser(name);
        model.addAttribute("Entities", lst);
        return "Admin";
    }

    @RequestMapping("GetStaffs")
    public String getStaffs(Model model) {
        List<UserEntity> lst = userService.getUsersByRole(Constant.ROLE_STAFF);
        model.addAttribute("Entities", lst);
        return "Admin";
    }

    @RequestMapping("GetAdmins")
    public String getAdmins(Model model) {
        List<UserEntity> lst = userService.getUsersByRole(Constant.ROLE_ADMIN);

        model.addAttribute("Entities", lst);
        return "Admin";
    }

    @RequestMapping("GetSupervisors")
    public String getSupervisors(Model model) {
        List<UserEntity> supervisorList = userService.getUsersByRole(Constant.ROLE_SUPERVISOR);
        model.addAttribute("Entities", supervisorList);
        return "Admin";
    }

    @RequestMapping("/ChangeActive")
    @ResponseBody
    public String deactiveUser(@RequestParam("txtUserid") Integer userid) {
        UserEntity user = userRepository.findOne(userid);
        //set user active = true khi active = false va nguoc lai
        user.setActive(!user.isActive());
        userRepository.save(user);
        return "success";
    }

    @RequestMapping("/GetUser")
    @ResponseBody
    public UserEntity getUser(@RequestParam("txtUserid") Integer userid) {
        return userRepository.findOne(userid);
    }

    @RequestMapping("/getcurrentuser")
    @ResponseBody
    public UserEntity getcurrentuser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity loginuser = userRepository.findUserByUsername(loginUser);

        return loginuser;
    }

    @RequestMapping("/user/createUserwProfile")
    @ResponseBody
    public void createUserWithProfile(HttpServletRequest request,
                                      @RequestParam("txtUsername") String username
            , @RequestParam("txtPassword") String password
            , @RequestParam("ddlRole") String role
            , @RequestParam("rdGender") String gender
            , @RequestParam("txtFirstname") String firstname
            , @RequestParam("txtLastname") String lastname
            , @RequestParam("txtAddress") String address
            , @RequestParam("txtPhone") String phone
            , @RequestParam("txtEmail") String email) {

        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity loginuser = userRepository.findUserByUsername(loginUser);


        UserEntity newUser = new UserEntity();
        if (username.length() > 0 && password.length() > 0 && role.length() > 0) {
            newUser.setUsername(username);
            newUser.setPassword(password);
            if (role.equals("sup")) newUser.setRoleid(Constant.ROLE_SUPERVISOR);
            else if (role.equals("staff")) newUser.setRoleid(Constant.ROLE_STAFF);
            newUser.setActive(true);
            //  newUser.setProfileid(1);
            newUser.setCreatedby(loginuser.getUserid());
            newUser.setBrandid(loginuser.getBrandid());
        }
        if (userRepository.save(newUser) != null) {


            ProfileEntity profileEntity = new ProfileEntity();
            if (firstname.length() > 0 && lastname.length() > 0) {
                profileEntity.setUserid(userService.getUserByUsername(newUser.getUsername()).getUserid());
                profileEntity.setFirstname(firstname);
                profileEntity.setLastname(lastname);

                //Aditional information
                if(address.length()>0)profileEntity.setAddress(address);
                if(phone.length()>0)profileEntity.setPhone(phone);
                if(email.length()>0)profileEntity.setEmail(email);

                if (gender.equals("male")) {
                    profileEntity.setGender(Constant.MALE);
                } else profileEntity.setGender(Constant.FEMALE);
            }

            profileRepository.save(profileEntity);

            System.out.println(newUser.getUserid());
        }
    }
}
