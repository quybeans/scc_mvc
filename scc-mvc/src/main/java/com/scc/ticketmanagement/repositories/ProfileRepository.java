package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 9/30/2016.
 */
public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer>{
}
