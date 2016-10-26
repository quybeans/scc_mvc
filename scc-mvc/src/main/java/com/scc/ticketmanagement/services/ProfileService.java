package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.ProfileEntity;

/**
 * Created by user on 9/30/2016.
 */
public interface ProfileService {

    ProfileEntity getProfileByID(Integer id);

    void createProfile(String firstname,String lastname,String address,String gender,
                       String phone,String email);

    void updateProfile(Integer profileID,String firstname,String lastname,String address,String gender,
                       String phone,String email);
}
