package com.example.interfaz_mvil.apistuff_users;

import com.example.interfaz_mvil.apistuff_users.user;
import com.example.interfaz_mvil.apistuff_users.userresponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService_usuario {

    @GET("usuarios")
    Call<List<user>> getUsuarios(@Header("Authorization") String bearertoken);

    @GET("usuarios/{id}")
    Call<user> getUserByID(@Header("Authorization") String bearertoken , @Path("id") String id);
    @FormUrlEncoded
    @POST("usuarios")
    Call<Void> registerUser(@Field("Username") String username,@Field("PasswordHash") String password,@Field("Email") String email);

    @FormUrlEncoded
    @PUT("usuarios/{id}")
    Call<Void> editUser(@Header("Authorization") String bearertoken ,@Path("id")String id,@Field("Id") String userID,@Field("Username") String username,@Field("PasswordHash") String password,@Field("Email") String email);




    // You can define more API endpoints for users as needed.
    //Call<List<user>> getUsuarios(@Header("Authorization") String bearertoken);
}
