package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by QuyBean on 11/11/2016.
 */
public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> {
    //Named Query
    List<ProfileEntity> findByProfileidIn (List<Integer> listProfileId);

    //Custom Query
}
