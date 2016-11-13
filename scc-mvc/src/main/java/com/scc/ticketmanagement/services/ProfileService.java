package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.ProfileEntity;

import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
public interface ProfileService {

    ProfileEntity getProfileByID(Integer id);

    ProfileEntity createProfile(String firstname, String lastname, String address, int gender, String phone, String email);

    void updateProfile(Integer profileid, String firstname, String lastname, String address, int gender, String phone, String email);

    List<ProfileEntity> findAllByid (List<Integer> profileIds);
}
