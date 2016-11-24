package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.ContactEntity;

/**
 * Created by Thien on 11/3/2016.
 */
public interface ContactService {
    public ContactEntity get(String id);
    public ContactEntity create(String fbId, String name, String picture);
    public ContactEntity update(String fbId, String name, String picture);
    public ContactEntity saveNote(String fbId, String name);
}
