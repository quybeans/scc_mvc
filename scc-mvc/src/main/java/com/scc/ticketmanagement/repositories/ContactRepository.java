package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Thien on 11/3/2016.
 */
public interface ContactRepository extends JpaRepository<ContactEntity, String> {

    @Query("SELECT c FROM  ContactEntity c WHERE c.facebookid =:facebookId")
    ContactEntity getContactById(@Param("facebookId") String facebookId);


    @Query("UPDATE ContactEntity c SET c.note=:content WHERE c.facebookid =:facebookId")
    @Modifying
    @Transactional
    Integer saveNote(@Param("facebookId") String facebookId, @Param("content") String content);
}
