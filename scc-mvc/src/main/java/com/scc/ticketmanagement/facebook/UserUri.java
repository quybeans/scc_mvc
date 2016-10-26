package com.scc.ticketmanagement.facebook;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

/**
 * Created by QuyBeans on 05-Oct-16.
 */
public class UserUri extends GenericUrl {
    @Key("access_token")
    private String accessToken;

    public UserUri(String encodedUrl) {
        super(encodedUrl);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static UserUri fromObjectId(String objectId) {
        return new UserUri("https://graph.facebook.com/" + objectId);
    }
}
