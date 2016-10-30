package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 10/30/2016.
 */
public interface AttributeRepository extends JpaRepository<AttributeEntity,Integer> {
}
