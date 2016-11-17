package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by QuyBean on 10/31/2016.
 */
public interface BrandRepository extends JpaRepository<BrandEntity,Integer> {

    @Query("SELECT COUNT(u) FROM BrandEntity u")
    Long countAll();

}
