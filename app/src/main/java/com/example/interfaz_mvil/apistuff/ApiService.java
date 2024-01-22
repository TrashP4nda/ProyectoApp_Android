package com.example.interfaz_mvil.apistuff;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    // Updated endpoint to "incidencia"
    @GET("incidences")
    Call<incidenciaresponse> getTrafficIncidences();
    @GET("incidences/byDate/{year}/{month}/{day}")
    Call<incidenciaresponse> getTrafficIncidencesByDate(@Path("day") String day,@Path("month") String month,@Path("year") String year);

    // If you have other specific endpoints, define them similarly
    // For example, to fetch a specific traffic incidence by ID:
    // @GET("incidencia/{id}")
    // Call<TrafficIncidence> getTrafficIncidence(@Path("id") String incidenceId);
}
