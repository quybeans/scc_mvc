package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.FacebookaccountEntity;

import java.util.List;

/**
 * Created by Thien on 10/6/2016.
 */
public interface FacebookaccountService {
    void createFacebookaccount(String facebookUserId, String accessToken, int userId, String username);
    FacebookaccountEntity getFacebookAccountByUid(String uid);
    List<FacebookaccountEntity> getFacebookaccountsByUserID(Integer userId);
    void deactivateFbAccount(String uid);
}
