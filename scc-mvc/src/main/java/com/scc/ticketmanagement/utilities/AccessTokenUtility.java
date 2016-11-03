package com.scc.ticketmanagement.utilities;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;
import com.scc.ticketmanagement.facebook.Contact;

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

    public static String getMessengerExtendedAccessToken(String accessToken) throws AuthenticationException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        FacebookClient.AccessToken extendedAccessToken = null;
        try {
            extendedAccessToken = facebookClient.obtainExtendedAccessToken("1151926771557379", "13190708bfffa558d0c35883d60bc125", accessToken);

        } catch (FacebookException e) {
            if (e.getMessage().contains("Error validating access token")) {
                throw new AuthenticationException("Error validating access token");
            }

            throw new RuntimeException("Error exchanging short lived token for long lived token", e);
        }
        return extendedAccessToken.getAccessToken();
    }



}
