package com.example.interfaz_mvil.tokenApi;

import com.google.gson.annotations.SerializedName;
import com.example.interfaz_mvil.apistuff_users.user;

public class tokenresponse {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private user userObject; // Reference to your existing user class

    public String getToken() {
        return token;
    }

    public user getUserObject() {
        return userObject;
    }
}
