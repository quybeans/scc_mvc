package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.UserfacebookaccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Thien on 10/29/2016.
 */
public interface UserfacebookaccountRepository extends JpaRepository<UserfacebookaccountEntity, Integer> {

    @Query("SELECT u FROM UserfacebookaccountEntity u WHERE u.userid =:userId AND u.facebookaccountid =:facebookAccountId")
    UserfacebookaccountEntity getUserfacebookaccount(@Param("userId") Integer userId,
                                                     @Param("facebookAccountId") Integer facebookAccountId);

    @Query("SELECT u FROM UserfacebookaccountEntity u WHERE u.userid =:userId")
    List<UserfacebookaccountEntity> getUserfacebookaccountsByUserId(@Param("userId") Integer userId);


}
