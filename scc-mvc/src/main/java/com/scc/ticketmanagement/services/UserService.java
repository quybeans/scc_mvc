package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.UserEntity;

import java.util.List;

/**
 * Created by Thien on 9/24/2016.
 */
public interface UserService {
    //get all user
    List<UserEntity> findAll();

    //Find all by brand
    List<UserEntity> findAllUserByBrand(int brandid);

    //check login
    UserEntity getUserByUsername(String username, String password);

    UserEntity findUser(String username, String password);

    UserEntity getUserByUsername(String username);

    Integer getBrandIdByUsername(String username);

    UserEntity findUserByBrandIdAndRole(int brandId, int role);

    void Delete(int id);

    void updateUser(Integer id, String username, String password, Integer roleid);

    void createUser(String username, String password, Integer roleid, Boolean active);

    UserEntity createUser(String username, String password, Integer roleid,Integer profileid,Boolean active);

    List<UserEntity> searchUser(String name);

    List<UserEntity> getUsersByRole(Integer roleId);

    UserEntity getUserByID(Integer id);

    void changeActive(Integer userid, boolean active);


}
