package com.scc.ticketmanagement.facebook;

import com.google.api.client.util.Key;

/**
 * Created by QuyBeans on 05-Oct-16.
 */
public class UserResponse {




    public static class User {
        @Key("id")
        private String objectId;

        @Key("name")
        private String name;

        public String getObjectId() {
            return this.objectId;
        }

        public String getName() {
            return this.name;
        }
    }
}
