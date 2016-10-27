package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.FacebookaccountEntity;
import com.scc.ticketmanagement.repositories.FacebookaccountRepository;
import com.scc.ticketmanagement.services.FacebookaccountService;
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

    @Override
    public void createFacebookaccount(String facebookUserId, String accessToken, int userId, String username) {
        FacebookaccountEntity facebookAccount = null;
        try {
            facebookAccount = this.getFacebookAccountByUid(facebookUserId);

            if (facebookAccount == null) {
                facebookAccount = new FacebookaccountEntity();
                facebookAccount.setFacebookuserid(facebookUserId);
                facebookAccount.setAccesstoken(accessToken);
                facebookAccount.setUserid(userId);
                facebookAccount.setFacebookusername(username);
                facebookAccount.setActive(true);
            }else {
                facebookAccount.setAccesstoken(accessToken);
            }
            repo.save(facebookAccount);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }



    @Override
    public FacebookaccountEntity getFacebookAccountByUid(String uid) {
        return repo.getFacebookaccountByUid(uid);
    }

    @Override
    public List<FacebookaccountEntity> getFacebookaccountsByUserID(Integer userId) {
        return repo.getFacebookaccountByUserId(userId);
    }

    @Override
    public void deactivateFbAccount(String uid) {
        repo.deactivateFacebookAccount(uid);
    }
}
