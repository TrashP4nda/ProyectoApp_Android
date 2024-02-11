package com.example.interfaz_mvil.apistuff_incidencia;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService_incidencia {

    // Updated endpoint to "incidencia"
    @GET("incidences")
    Call<incidenciaresponse> getTrafficIncidences(@Query("_page") int page);
    @GET("incidences/byDate/{year}/{month}/{day}")
    Call<incidenciaresponse> getTrafficIncidencesByDate(@Path("day") String day,@Path("month") String month,@Path("year") String year);

    @GET("incidences/byDate/{year}/{month}/{day}/byLocation/{lat}/{lon}/{km}")
    Call<incidenciaresponse> getTrafficIncidencesByDateAndLocation(@Path("day") String day,@Path("month") String month,@Path("year") String year, @Path("lat")double lat ,@Path("lon") double lon , @Path("km") int km);

    @FormUrlEncoded
    @POST("addIncidenciaFavorite/{userId}")
    Call<Void> addIncidenciaAndFavorite(@Header("Authorization") String bearertoken ,
                                    @Path("userId") String userId,
                                    @Field("AutonomousRegion") String AutonomousRegion,
                                    @Field("CarRegistration") String CarRegistration,
                                    @Field("Cause") String Cause,
                                    @Field("CityTown") String CityTown,
                                    @Field("Direction") String Direction,
                                    @Field("endDate") String endDate,
                                    @Field("incidenceDescription") String incidenceDescription,
                                    @Field("incidenceID") String incidenceID,
                                    @Field("incidenceLevel") String incidenceLevel);


    // If you have other specific endpoints, define them similarly
    // For example, to fetch a specific traffic incidence by ID:
    // @GET("incidencia/{id}")
    // Call<TrafficIncidence> getTrafficIncidence(@Path("id") String incidenceId);
}
