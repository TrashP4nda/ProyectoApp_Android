package com.example.interfaz_mvil.apistuff_camara;

import com.example.interfaz_mvil.apistuff_incidencia.incidenciaresponse;
import com.example.interfaz_mvil.combined.CombinedFavoritesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService_camara {

    // Updated endpoint to "camera"
    @GET("cameras")
    Call<camararesponse> getCameras(@Query("_page") int page);

    @GET("cameras/byLocation/{lat}/{lon}/{km}")
    Call<camararesponse> getCamerasByLocation(@Path("lat") double lat,@Path("lon") double lon,@Path("km") int km);


    @FormUrlEncoded
    @POST("addCameraFavorite/{userId}")
    Call<Void> addCameraAndFavorite(@Header("Authorization") String bearertoken ,
                                    @Path("userId") String userId,
                                    @Field("CameraId") String cameraId,
                                    @Field("Address") String address,
                                    @Field("CameraName") String cameraName,
                                    @Field("Kilometer") String kilometer,
                                    @Field("Latitude") String latitude,
                                    @Field("Longitude") String longitude,
                                    @Field("url") String url);

    @GET("{userID}/favorites")
    Call<CombinedFavoritesResponse> getCombinedFavorites(@Header("Authorization") String bearerToken,@Path("userID") String userID);



   // @GET("incidences/byDate/{year}/{month}/{day}")
   // Call<incidenciaresponse> getTrafficIncidencesByDate(@Path("day") String day,@Path("month") String month,@Path("year") String year);

    // If you have other specific endpoints, define them similarly
    // For example, to fetch a specific traffic incidence by ID:
    // @GET("incidencia/{id}")
    // Call<TrafficIncidence> getTrafficIncidence(@Path("id") String incidenceId);
}
