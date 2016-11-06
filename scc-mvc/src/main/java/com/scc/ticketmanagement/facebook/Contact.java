package com.scc.ticketmanagement.facebook;

import com.restfb.Facebook;

/**
 * Created by Thien on 11/3/2016.
 */
public class Contact {
    @Facebook("first_name")
    private String first_name;

    @Facebook("timezone")
    private String timezone;

    @Facebook("locale")
    private String locale;

    @Facebook("last_name")
    private String last_name;

    @Facebook("gender")
    private String gender;

    @Facebook("profile_pic")
    private String profile_pic;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    @Override
    public String toString() {
        return "ClassPojo [first_name = " + first_name + ", timezone = " + timezone + ", locale = " + locale + ", last_name = " + last_name + ", gender = " + gender + ", profile_pic = " + profile_pic + "]";
    }
}