package com.scc.ticketmanagement.utilities;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.scc.ticketmanagement.facebook.UserResponse;
import com.scc.ticketmanagement.facebook.UserUri;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuyBeans on 05-Oct-16.
 */
public class FacebookUtility {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     *
     * @param fbId: ID of the facebook account
     * @param access_token: access_token must have
     * @return
     */
    public static String getFBName(String fbId, String access_token){
        try {
            HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
            UserUri url = UserUri.fromObjectId(fbId);
            url.setAccessToken(access_token);

            HttpRequest postsRequest = requestFactory.buildGetRequest(url);
            postsRequest.setParser(new JsonObjectParser(JSON_FACTORY));

            UserResponse.User res = postsRequest.execute().parseAs(UserResponse.User.class);
            return res.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param object : ID of target object
     * @param message: message String
     * @param access_token: user access token to send
     * @return
     */

    public static boolean commentOnObj(String object, String message, String access_token)
    {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost =  new HttpPost("https://graph.facebook.com/"+object+"/comments/");

        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("message", message));
        params.add(new BasicNameValuePair("access_token", access_token));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            //Execute and get the response.
            org.apache.http.HttpResponse response = httpclient.execute(httpPost);
            if (response!=null)
                return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return false;
    }

}
