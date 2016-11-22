package com.scc.ticketmanagement.utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by QuyBean on 11/16/2016.
 */
public class CommonUtility {

    public static String hashStringToMD5(String input) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(input.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse makeHttpRequest(String url)
    {
        try{
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        System.out.println(url);
        org.apache.http.HttpResponse response = httpclient.execute(httpGet);

        return response;}
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
