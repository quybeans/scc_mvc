package com.scc.ticketmanagement.utilities;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.scc.ticketmanagement.facebook.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.naming.AuthenticationException;
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
     * @param fbId:         ID of the facebook account
     * @param access_token: access_token must have
     * @return
     */
    public static String getFBName(String fbId, String access_token) {
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
     * @param object        : ID of target object
     * @param message:      message String
     * @param access_token: user access token to send
     * @return
     */

    public static String commentOnObj(String object, String message, String access_token) {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://graph.facebook.com/" + object + "/comments/");

        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("message", message));
        params.add(new BasicNameValuePair("access_token", access_token));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            //Execute and get the response.
            org.apache.http.HttpResponse response = httpclient.execute(httpPost);
            if (response != null) {
                String json = EntityUtils.toString(response.getEntity());
                JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
                String rs = jsonObject.get("id").getAsString();
                return rs;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * @param pageAccessToken: a page Access Token String get by app (SCC Messenger)
     * @return
     */
    public static boolean subscribePageToWebhook(String pageId, String pageAccessToken) {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://graph.facebook.com/" + pageId + "/subscribed_apps");

        List<NameValuePair> params = new ArrayList<>(1);
        params.add(new BasicNameValuePair("access_token", pageAccessToken));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            org.apache.http.HttpResponse response = httpclient.execute(httpPost);

            if (response != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("RESULT:");
                    System.out.println(response.getEntity());
                    return true;
                } else {
                    System.out.println(response.getStatusLine().getStatusCode());
                    System.out.println(response);
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String sendMessage(String content, String recipientId, String accessToken) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://graph.facebook.com/me/messages?access_token=" + accessToken);
        Gson gson = new Gson();

        Payload.Recipient recipient = new Payload.Recipient(recipientId);
        Payload.Message message = new Payload.Message(content);

        Payload payloads = new Payload(recipient, message);

        StringEntity payload = new StringEntity(gson.toJson(payloads), "UTF-8");

        httpPost.setEntity(payload);


//        httpPost.setEntity(new UrlEncodedFormEntity(payload, "UTF-8"));
        httpPost.setHeader("Content-type", "application/json");

        HttpResponse response = httpclient.execute(httpPost);

        if (response != null) {
            String json = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            String rs = jsonObject.get("message_id").getAsString();
            System.out.println("result " + rs);
            return rs;
        }

        return null;
    }

    public static class Payload {
        public Recipient recipient;
        public Message message;

        public Payload(Recipient recipient, Message message) {
            this.recipient = recipient;
            this.message = message;
        }

        public static class Recipient {
            public String id;

            public Recipient(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class Message {
            public String text;

            public Message(String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }


    public static Contact getContact(String senderId, String accessToken) throws AuthenticationException {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
        Contact contact = facebookClient.fetchObject(senderId, Contact.class);
        return contact;
    }

    public static String getProfileId(String avtId) throws AuthenticationException {
        FacebookClient facebookClient = new DefaultFacebookClient(Constant.NEVER_EXPIRED_USER_TOKEN);
        FacebookProfile profile = facebookClient.fetchObject(avtId, FacebookProfile.class, Parameter.with("fields", "from"));
        return profile.getFrom().getId();
    }

    public static String getShortObjectId(String fullObjectId) {
        if (null == fullObjectId) {
            return null;
        }

        String[] objectIdParts = fullObjectId.split("_");

        return objectIdParts[objectIdParts.length - 1];
    }

    public static String getParentShortObjectId(String fullObjectId) {
        if (null == fullObjectId) {
            return null;
        }

        String[] objectIdParts = fullObjectId.split("_");

        return objectIdParts[objectIdParts.length - 2];
    }

}
