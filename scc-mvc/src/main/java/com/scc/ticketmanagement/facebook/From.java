package com.scc.ticketmanagement.facebook;

import com.restfb.Facebook;

/**
 * Created by Thien on 11/13/2016.
 */
public class From
{
    @Facebook("id")
    private String id;

    @Facebook("name")
    private String name;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+"]";
    }
}