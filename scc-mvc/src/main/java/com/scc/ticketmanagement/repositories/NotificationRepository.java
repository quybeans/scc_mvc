package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by QuyBean on 11/2/2016.
 */
public interface NotificationRepository extends JpaRepository<NotificationEntity,Integer> {
}
