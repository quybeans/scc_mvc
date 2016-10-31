package com.scc.ticketmanagement.repository;

import com.scc.ticketmanagement.entity.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 10/30/2016.
 */
public interface AttributeRepository extends JpaRepository<AttributeEntity,Integer> {
}
