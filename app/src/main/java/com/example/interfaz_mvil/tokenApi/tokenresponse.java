package com.example.interfaz_mvil.tokenApi;

import com.google.gson.annotations.SerializedName;

public class tokenresponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
