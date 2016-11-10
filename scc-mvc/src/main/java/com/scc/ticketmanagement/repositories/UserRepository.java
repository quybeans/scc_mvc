package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Thien on 9/24/2016.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByBrandidAndRoleidEquals(int brandid, int roleid);
    //find user brand by username
    @Query("SELECT u.brandid FROM UserEntity u WHERE u.username = :username")
    Integer getBrandIdByUsername(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    UserEntity findUserByUsername(@Param("username") String username);

    //find brandid user by username
    @Query("SELECT u.brandid FROM UserEntity u WHERE u.username = :username")
    int findBrandByUser(@Param("username") String username);

    //find userid by username
    @Query("SELECT u.userid FROM UserEntity u WHERE u.username = :username")
    int findUseridByUser(@Param("username") String username);

    //find user by username and password
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password")
    UserEntity findUserByUsernameAndPassword(@Param("username") String username,
                                             @Param("password") String password);



    @Query("SELECT u FROM UserEntity u WHERE u.username like %:username%")
    List<UserEntity> searchUser(@Param("username") String name);

    @Query("SELECT u FROM UserEntity u WHERE u.roleid = :roleid")
    List<UserEntity> getUsersByRole(@Param("roleid") Integer roleid);

    @Query("UPDATE UserEntity set active=:active where userid=:userid")
    @Modifying
    @Transactional
     void changeActive(@Param("userid") Integer userid,
                        @Param("active") boolean active);

    @Query("Select u from UserEntity u where u.brandid=:brandid")
    List<UserEntity> getAllUserInBrand(@Param("brandid") Integer brandid);
}
