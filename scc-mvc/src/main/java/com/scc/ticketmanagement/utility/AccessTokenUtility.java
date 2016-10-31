package com.scc.ticketmanagement.utility;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;

import javax.naming.AuthenticationException;




/**
 * Created by user on 10/5/2016.
 */
public class AccessTokenUtility {
    public static String getExtendedAccessToken(String accessToken) throws AuthenticationException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        FacebookClient.AccessToken extendedAccessToken = null;
        try {
            extendedAccessToken = facebookClient.obtainExtendedAccessToken(Constant.APP_ID, Constant.APP_SECRET, accessToken);

        } catch (FacebookException e) {
            if (e.getMessage().contains("Error validating access token")) {
                throw new AuthenticationException("Error validating access token");
            }

            throw new RuntimeException("Error exchanging short lived token for long lived token", e);
        }
        return extendedAccessToken.getAccessToken();
    }


}
