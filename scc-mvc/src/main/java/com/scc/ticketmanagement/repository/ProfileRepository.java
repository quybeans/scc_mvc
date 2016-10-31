package com.scc.ticketmanagement.repository;

import com.scc.ticketmanagement.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 9/30/2016.
 */
public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer>{
}
