package com.scc.ticketmanagement.repository;

import com.scc.ticketmanagement.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 10/30/2016.
 */
public interface PriorityReposioty extends JpaRepository<PriorityEntity,Integer> {

    @Query("select p from PriorityEntity p where p.brandid=:brandid")
    List<PriorityEntity> findBybrandid(@Param("brandid") Integer brandid);
}
