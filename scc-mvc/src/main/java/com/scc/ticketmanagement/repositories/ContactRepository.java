package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Thien on 11/3/2016.
 */
public interface ContactRepository extends JpaRepository<ContactEntity, String> {

    @Query("SELECT c FROM  ContactEntity c WHERE c.facebookid =:facebookId")
    ContactEntity getContactById(@Param("facebookId") String facebookId);
}
