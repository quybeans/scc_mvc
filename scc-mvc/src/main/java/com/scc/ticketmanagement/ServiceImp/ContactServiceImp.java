package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.ContactEntity;
import com.scc.ticketmanagement.repositories.ContactRepository;
import com.scc.ticketmanagement.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Thien on 11/3/2016.
 */
public class ContactServiceImp implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public ContactEntity get(String id) {
        return contactRepository.getContactById(id);
    }

    @Override
    public ContactEntity create(String fbId, String name, String picture) {
        ContactEntity contact = new ContactEntity();
        contact.setFacebookid(fbId);
        contact.setName(name);
        contact.setPicture(picture);
        return contactRepository.saveAndFlush(contact);
    }

    @Override
    public ContactEntity update(String fbId, String name, String picture) {
        ContactEntity contact = contactRepository.findOne(fbId);
        if (contact!= null){
            contact.setName(name);
            contact.setPicture(picture);
        }

        return contactRepository.saveAndFlush(contact);
    }

    @Override
    public ContactEntity saveNote(String fbId, String name) {
        ContactEntity contact = contactRepository.findOne(fbId);
        if (contact!= null){
            contact.setNote(name);
        }

        return contactRepository.saveAndFlush(contact);
    }
}
