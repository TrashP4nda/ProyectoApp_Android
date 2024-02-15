package com.example.interfaz_mvil.apistuff_customIncidencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService_customIncidencia {

    // Updated endpoint to "incidencia"
    @GET("customIncidencias")
    Call<List<customIncidencia>> getCustomIncidencias(@Header("Authorization") String bearertoken);}

