package com.example.interfaz_mvil.apistuff_users;

import com.example.interfaz_mvil.apistuff_users.user;
import com.example.interfaz_mvil.apistuff_users.userresponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiService_usuario {

    @GET("usuarios")
    Call<List<user>> getUsuarios(@Header("Authorization") String bearertoken);





    // You can define more API endpoints for users as needed.
    //Call<List<user>> getUsuarios(@Header("Authorization") String bearertoken);
}
