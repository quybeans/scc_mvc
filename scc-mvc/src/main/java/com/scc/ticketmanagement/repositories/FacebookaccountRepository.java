package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.FacebookaccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Thien on 10/6/2016.
 */

public interface FacebookaccountRepository extends JpaRepository<FacebookaccountEntity, Integer> {
    @Query("select f from FacebookaccountEntity f where f.facebookuserid = :uid")
    FacebookaccountEntity getFacebookaccountByUid(@Param("uid") String uid);

    @Query("select f from FacebookaccountEntity f where f.userid = :userid and f.active = true")
    List<FacebookaccountEntity> getFacebookaccountByUserId(@Param("userid") Integer userid);

    @Query("update FacebookaccountEntity f set f.active = false where f.facebookuserid =:uid")
    @Modifying
    @Transactional
    void deactivateFacebookAccount(@Param("uid") String uid);

//    @Query("update FacebookaccountEntity f set f.active = false where f.userid =: uid")
//    FacebookaccountEntity updateFacebookAccount(@Param("uid") String uid);
}
