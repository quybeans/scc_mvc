package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.TicketEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.TicketRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.CommonUtility;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thien on 9/24/2016.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();

    }

    @Override
    public UserEntity findUser(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, CommonUtility.hashStringToMD5(password));
    }

    @Override
    public UserEntity getUserByUsername(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Integer getBrandIdByUsername(String username) {
        return userRepository.getBrandIdByUsername(username);
    }

    @Override
    public UserEntity findUserByBrandIdAndRole(int brandId, int role) {
        return userRepository.findByBrandidAndRoleidEquals(brandId,role);
    }

    @Override
    public void Delete(int id) {
        userRepository.delete(id);
    }

    @Override
    public void updateUser(Integer id, String username, String password, Integer roleid) {
        UserEntity userEntity = userRepository.findOne(id);
        if (!username.equals("")){
            userEntity.setUsername(username);
        }
        if (!password.equals("")){
            userEntity.setPassword(CommonUtility.hashStringToMD5(password));
        }
        if (roleid!=0){
            userEntity.setRoleid(roleid);
        }


        userRepository.save(userEntity);
    }

    @Override
    public void createUser(String username, String password, Integer roleid, Boolean active) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setRoleid(roleid);
        userEntity.setActive(true);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity createUser(String username, String password, Integer roleid,Integer profileid,Boolean active) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setRoleid(roleid);
        userEntity.setActive(true);
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> searchUser(String name) {

        return userRepository.searchUser(name);
    }

    @Override
    public List<UserEntity> getUsersByRole(Integer roleId) {
        return userRepository.getUsersByRole(roleId);
    }

    @Override
    public UserEntity getUserByID(Integer userID){
        return userRepository.findOne(userID);
    }

    @Override
    public void changeActive(Integer userid,boolean active) {
        userRepository.changeActive(userid,active);
        if(active==false){
            UserEntity deactiveuser = userRepository.findOne(userid);
            UserEntity brandmanager = userRepository.getBrandManager(Constant.ROLE_BRAND,deactiveuser.getBrandid());
            List<TicketEntity> ticketlist = ticketRepository.getListTicketOfUser(userid);
            for (TicketEntity tk:ticketlist) {
                tk.setAssignee(brandmanager.getUserid());
                tk.setCreatedby(brandmanager.getUserid());
                tk.setStatusid(Constant.STATUS_ASSIGN);
                ticketRepository.save(tk);
            }
        }
    }

    @Override
    public List<UserEntity> findAllUserByBrand(int brandid) {

        return userRepository.findByBrandid(brandid);
    }


}
