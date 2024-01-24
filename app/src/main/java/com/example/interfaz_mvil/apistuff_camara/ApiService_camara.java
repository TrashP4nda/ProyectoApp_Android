package com.example.interfaz_mvil.apistuff_camara;

import com.example.interfaz_mvil.apistuff_incidencia.incidenciaresponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService_camara {

    // Updated endpoint to "camera"
    @GET("cameras")
    Call<camararesponse> getCameras(@Query("_page") int page);

    @GET("cameras/byLocation/{lat}/{lon}/{km}")
    Call<camararesponse> getCamerasByLocation(@Path("lat") double lat,@Path("lon") double lon,@Path("km") int km);


   // @GET("incidences/byDate/{year}/{month}/{day}")
   // Call<incidenciaresponse> getTrafficIncidencesByDate(@Path("day") String day,@Path("month") String month,@Path("year") String year);

    // If you have other specific endpoints, define them similarly
    // For example, to fetch a specific traffic incidence by ID:
    // @GET("incidencia/{id}")
    // Call<TrafficIncidence> getTrafficIncidence(@Path("id") String incidenceId);
}
