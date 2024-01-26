package com.example.interfaz_mvil.tokenApi;

import com.example.interfaz_mvil.apistuff_users.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService_token {
    @FormUrlEncoded
    @POST("usuarios/login")
    Call<tokenresponse> Login(@Field("Username") String username, @Field("Password") String password);





    // You can define more API endpoints for users as needed.
    //Call<List<user>> getUsuarios(@Header("Authorization") String bearertoken);

}
