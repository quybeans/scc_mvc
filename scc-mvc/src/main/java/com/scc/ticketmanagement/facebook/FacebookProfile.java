package com.scc.ticketmanagement.facebook;

import com.restfb.Facebook;

/**
 * Created by Thien on 11/13/2016.
 */
public class FacebookProfile
{
    @Facebook("id")
    private String id;

    @Facebook("from")
    private From from;

    @Facebook("created_time")
    private String created_time;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public From getFrom ()
    {
        return from;
    }

    public void setFrom (From from)
    {
        this.from = from;
    }

    public String getCreated_time ()
    {
        return created_time;
    }

    public void setCreated_time (String created_time)
    {
        this.created_time = created_time;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", from = "+from+", created_time = "+created_time+"]";
    }
}