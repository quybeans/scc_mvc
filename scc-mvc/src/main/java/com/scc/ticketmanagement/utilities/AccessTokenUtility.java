package com.scc.ticketmanagement.utilities;

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
            extendedAccessToken = facebookClient.obtainExtendedAccessToken("341786092825787", "c07230dd847bc69613a4aa0104d422a8", accessToken);


        } catch (FacebookException e) {
            if (e.getMessage().contains("Error validating access token")) {
                throw new AuthenticationException("Error validating access token");
            }

            throw new RuntimeException("Error exchanging short lived token for long lived token", e);
        }
        return extendedAccessToken.getAccessToken();
    }


}
