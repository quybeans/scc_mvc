package com.scc.ticketmanagement.serviceImplement;

import com.scc.ticketmanagement.entity.UserfacebookaccountEntity;
import com.scc.ticketmanagement.repository.UserfacebookaccountRepository;
import com.scc.ticketmanagement.service.UserfacebookaccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thien on 10/29/2016.
 */
@Service
public class UserFacebookAccountServiceImp implements UserfacebookaccountService {

    @Autowired
    UserfacebookaccountRepository repository;

    @Override
    public UserfacebookaccountEntity get(int userId, int facebookAccountId) {
        return repository.getUserfacebookaccount(userId, facebookAccountId);
    }

    @Override
    public UserfacebookaccountEntity create(int userId, int facebookAccountId) {
        UserfacebookaccountEntity entity = repository.getUserfacebookaccount(userId, facebookAccountId);

       if (entity == null){
           entity = new UserfacebookaccountEntity();
           entity.setUserid(userId);
           entity.setFacebookaccountid(facebookAccountId);
           entity.setActive(true);
           repository.save(entity);
           repository.flush();
       }


        return entity;
    }

    @Override
    public UserfacebookaccountEntity update(int userId, int facebookAccountId) {
        return null;
    }

    @Override
    public UserfacebookaccountEntity deactivate(int userId, int facebookAccountId) {
        UserfacebookaccountEntity entity = repository.getUserfacebookaccount(userId, facebookAccountId);
        if (entity!=null){
            entity.setActive(false);
            repository.saveAndFlush(entity);
        }
        return entity;
    }

    @Override
    public UserfacebookaccountEntity activate(int userId, int facebookAccountId) {
        UserfacebookaccountEntity entity = repository.getUserfacebookaccount(userId, facebookAccountId);
        if (entity!=null){
            entity.setActive(true);
            repository.saveAndFlush(entity);
        }
        return entity;
    }


}
