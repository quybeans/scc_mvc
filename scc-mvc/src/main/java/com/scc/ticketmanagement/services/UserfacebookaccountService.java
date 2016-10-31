package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.UserfacebookaccountEntity;

/**
 * Created by Thien on 10/29/2016.
 */
public interface UserfacebookaccountService {
    UserfacebookaccountEntity get(int userId, int facebookAccountId);
    UserfacebookaccountEntity create(int userId, int facebookAccountId);
    UserfacebookaccountEntity update(int userId, int facebookAccountId);
    UserfacebookaccountEntity deactivate(int userId, int facebookAccountId);
    UserfacebookaccountEntity activate(int userId, int facebookAccountId);
}