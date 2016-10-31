package com.scc.ticketmanagement.serviceImplement;

import com.scc.ticketmanagement.entity.ProfileEntity;
import com.scc.ticketmanagement.repository.ProfileRepository;
import com.scc.ticketmanagement.service.ProfileService;
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
    public ProfileEntity createProfile(String firstname, String lastname, String address, String gender, String phone, String email) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setFirstname(firstname);
        profileEntity.setLastname(lastname);
        profileEntity.setAddress(address);
        profileEntity.setGender(gender);
        profileEntity.setPhone(phone);
        profileEntity.setEmail(email);
        return profileRepository.save(profileEntity);
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
