package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.UserfacebookaccountEntity;

import java.util.List;

/**
 * Created by Thien on 10/29/2016.
 */
public interface UserfacebookaccountService {
    UserfacebookaccountEntity get(int userId, int facebookAccountId);
    List<UserfacebookaccountEntity> get(int userId);
    UserfacebookaccountEntity create(int userId, int facebookAccountId);
    UserfacebookaccountEntity update(int userId, int facebookAccountId);
    UserfacebookaccountEntity deactivate(int userId, int facebookAccountId);
    UserfacebookaccountEntity activate(int userId, int facebookAccountId);
}
