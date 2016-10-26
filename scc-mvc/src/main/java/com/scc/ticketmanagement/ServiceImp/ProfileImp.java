package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.repositories.ProfileRepository;
import com.scc.ticketmanagement.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by user on 9/30/2016.
 */
@Service
public class ProfileImp implements ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    @Override
    public ProfileEntity getProfileByID(Integer id) {
        return profileRepository.findOne(id);
    }

    @Override
    public void createProfile(String firstname, String lastname, String address, String gender, String phone, String email) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setFirstname(firstname);
        profileEntity.setLastname(lastname);
        profileEntity.setAddress(address);
        profileEntity.setGender(gender);
        profileEntity.setPhone(phone);
        profileEntity.setEmail(email);
        profileRepository.save(profileEntity);
    }

    @Override
    public void updateProfile(Integer profileid,String firstname, String lastname, String address, String gender, String phone, String email) {
        ProfileEntity profileEntity = profileRepository.findOne(profileid);
        profileEntity.setFirstname(firstname);
        profileEntity.setLastname(lastname);
        profileEntity.setAddress(address);
        profileEntity.setGender(gender);
        profileEntity.setPhone(phone);
        profileEntity.setEmail(email);
        profileRepository.save(profileEntity);
    }
}
