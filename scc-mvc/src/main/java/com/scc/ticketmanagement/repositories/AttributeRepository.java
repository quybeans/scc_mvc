package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 10/30/2016.
 */
public interface AttributeRepository extends JpaRepository<AttributeEntity,Integer> {

    @Query("select a from AttributeEntity a where a.brandid=:brandid")
    List<AttributeEntity> getAttributeByBrandID(@Param("brandid") Integer brandid);


}
