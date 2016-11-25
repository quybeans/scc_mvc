package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.UserfacebookaccountEntity;
import com.scc.ticketmanagement.repositories.UserfacebookaccountRepository;
import com.scc.ticketmanagement.services.UserfacebookaccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<UserfacebookaccountEntity> get(int userId) {
        return repository.getUserfacebookaccountsByUserId(userId);
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

    @Override
    public Integer deleteAccount(int userId, int facebookAccountId) {
        return repository.deleteAccount(userId, facebookAccountId);
    }


}
