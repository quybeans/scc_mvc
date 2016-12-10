package com.scc.ticketmanagement.utilities;

/**
 * Created by Thien on 9/26/2016.
 */
public class Constant {
    public static final String SCC_CRAWLER_HOST = "http://localhost:8080";


    //Scc-Staff
    public static final String APP_ID = "341786092825787";
    public static final String APP_SECRET = "c07230dd847bc69613a4aa0104d422a8";

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_BRAND = 2;
    public static final int ROLE_SUPERVISOR = 3;
    public static final int ROLE_STAFF = 4;

    public static final int STATUS_ASSIGN = 1;
    public static final int STATUS_INPROCESS = 2;
    public static final int STATUS_SOLVED = 3;
    public static final int STATUS_CLOSE = 4;
    public static final int STATUS_EXPIRED = 5;
    public static final int STATUS_FORWARD = 6;
    public static final int STATUS_REOPEN = 7;
    public static final int STATUS_CREATE = 8;
    public static final int STATUS_REASSIGN= 9;

    public static final boolean ACTIVE = true;
    public static final boolean DEACTIVE = false;

    public static final int PAGE_SIZE = 15;

    public static final int MALE = 1;
    public static final int FEMALE = 2;

    public static final String NEVER_EXPIRED_USER_TOKEN = "EAAE22kam7LsBAMYrrkQ4D1GzKZB9qzILLyOasfYKr3JZCGZCK2Kut5EWn0mr49eUpUmp0ZA7FU4XuAjHSW9SPgNRikWpobwFOanyazrx2Gkv2jE9JxIBX4lqR2cDb2IYaDNNYV94U5ad5wdL4iFnId39n4aCwyUZD";

    public static final String CRAWLER_TOKEN_SETTINGS = "crawler_token";
    public static final String CRAWLER_TIME_LIMIT = "crawler_time_limit";

    public static final int SENTIMENT_POSITIVE = 1;
    public static final int SENTIMENT_NEGATIVE = 2;
    public static final int SENTIMENT_QUESTION = 3;
}
