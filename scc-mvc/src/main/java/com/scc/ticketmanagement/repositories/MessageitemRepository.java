package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.MessageitemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Thien on 11/8/2016.
 */
public interface MessageitemRepository extends JpaRepository<MessageitemEntity, Integer> {

    @Query("SELECT m FROM MessageitemEntity m WHERE m.id = :itemId")
    MessageitemEntity get(@Param("itemId") Integer itemId);
}
