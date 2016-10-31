package com.scc.ticketmanagement.serviceImplement;

import com.scc.ticketmanagement.entity.FacebookaccountEntity;
import com.scc.ticketmanagement.repository.FacebookaccountRepository;
import com.scc.ticketmanagement.service.FacebookaccountService;
import com.scc.ticketmanagement.service.UserfacebookaccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thien on 10/6/2016.
 */
@Service
public class FacebookaccountImp implements FacebookaccountService {
    @Autowired
     FacebookaccountRepository repo;

    @Autowired
    UserfacebookaccountService userfacebookaccountService;

    @Override
    public void createFacebookaccount(String facebookUserId, String accessToken, int userId, String username) {
        FacebookaccountEntity facebookAccount = null;
        try {
            facebookAccount = this.getFacebookAccountByUid(facebookUserId);

            if (facebookAccount == null) {
                facebookAccount = new FacebookaccountEntity();
                facebookAccount.setFacebookuserid(facebookUserId);
                facebookAccount.setAccesstoken(accessToken);
                facebookAccount.setFacebookusername(username);
                facebookAccount.setActive(true);
            }else {
                facebookAccount.setAccesstoken(accessToken);
                facebookAccount.setActive(true);
            }
            repo.saveAndFlush(facebookAccount);
            userfacebookaccountService.create(userId, facebookAccount.getFacebookaccountid());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }



    @Override
    public FacebookaccountEntity getFacebookAccountByUid(String uid) {
        return repo.getFacebookaccountByUid(uid);
    }


    @Override
    public void deactivateFbAccount(String uid) {
        repo.deactivateFacebookAccount(uid);
    }

    @Override
    public List<FacebookaccountEntity> getFacebookAccountsByUserId(int userId) {
        return repo.getFacebookAccountsByUserId(userId);
    }

}
