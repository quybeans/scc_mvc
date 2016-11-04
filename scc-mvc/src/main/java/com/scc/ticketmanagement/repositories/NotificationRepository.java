package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by QuyBean on 11/2/2016.
 */
public interface NotificationRepository extends JpaRepository<NotificationEntity,Integer> {

    @Query("SELECT n FROM NotificationEntity n where n.userid = :userid")
    List<NotificationEntity> finaAllByUserid(@Param("userid") int userid);


}
