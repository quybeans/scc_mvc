package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.UserEntity;

import java.util.List;

/**
 * Created by Thien on 9/24/2016.
 */
public interface UserService {
    //get all user
     List<UserEntity> findAll();

    //check login
     UserEntity findUser(String username, String password);

     void Delete(int id);

     void updateUser(Integer id, String username, String password, Integer roleid);

     void createUser(String username, String password, Integer roleid, Boolean active);

     List<UserEntity> searchUser(String name);

     List<UserEntity> getUsersByRole(Integer roleId);

    UserEntity getUserByID(Integer id);

    void changeActive(Integer userid,boolean active);
}
