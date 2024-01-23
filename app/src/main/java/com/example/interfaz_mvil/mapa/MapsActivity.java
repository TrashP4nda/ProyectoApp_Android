package com.example.interfaz_mvil.mapa;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.interfaz_mvil.R;
import com.example.interfaz_mvil.apistuff_camara.ApiService_camara;
import com.example.interfaz_mvil.apistuff_camara.camara;
import com.example.interfaz_mvil.apistuff_camara.camararesponse;
import com.example.interfaz_mvil.infowindow.CustomInfoWindowAdapter;
import com.example.interfaz_mvil.recyclerview_camaras.Adapter_camaras;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.interfaz_mvil.databinding.ActivityMapsBinding;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private Adapter_camaras adapterCamaras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.euskadi.eus/traffic/v1.0/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService_camara apiServiceCamera = retrofit.create(ApiService_camara.class);


        apiServiceCamera.getCameras(6).enqueue(new Callback<camararesponse>() {
            @Override
            public void onResponse(Call<camararesponse> call, Response<camararesponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<camara> cameras = response.body().getCamaras();
                    for (camara camera : cameras) {
                        Log.e("asdasdasd",camera.getLatitude());
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Float.valueOf(camera.getLatitude()), Float.valueOf(camera.getLongitude())))
                                .title(camera.getCameraId()) // Ensure this is not null
                                .snippet(camera.getCameraName())); // Ensure this is not null
                       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.valueOf(camera.getLatitude()), Float.valueOf(camera.getLongitude())),10.0f));   /// IMPORTANTE

                    }

                } else {
                    // Handle the case where the response was not successful
                    Log.e("API Error", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<camararesponse> call, Throwable t) {
                // Handle the case where the request failed to execute
                Log.e("API Error", "Request failed", t);
            }
        });

    }


    private void addCustomMarker(LatLng position, String title) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title(title);

        mMap.addMarker(markerOptions);
    }
}