package com.scc.ticketmanagement.exentities;

/**
 * Created by user on 10/25/2016.
 */
public class User {
    private int userid;
    private String lastname;
    private String firstname;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
