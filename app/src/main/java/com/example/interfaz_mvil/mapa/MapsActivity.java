package com.example.interfaz_mvil.mapa;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.interfaz_mvil.R;
import com.example.interfaz_mvil.apistuff_camara.ApiService_camara;
import com.example.interfaz_mvil.apistuff_camara.camara;
import com.example.interfaz_mvil.apistuff_camara.camararesponse;
import com.example.interfaz_mvil.apistuff_incidencia.ApiService_incidencia;
import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.example.interfaz_mvil.apistuff_incidencia.incidenciaresponse;
import com.example.interfaz_mvil.infowindow.CustomInfoWindowAdapter;
import com.example.interfaz_mvil.recyclerview_camaras.Adapter_camaras;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.interfaz_mvil.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.euskadi.eus/traffic/v1.0/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create()).build();

    Retrofit retrofitForCustom = new Retrofit.Builder().baseUrl("http://192.168.1.136:5009/api/usuarios/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create()).build();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));

        getSurroundingCameras();
        getSurroundingIncidencesByDateAndLocation();


        // Inside the onMapReady method or after the map has been initialized
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // Create an AlertDialog Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Confirma la acción");

                String snippet = marker.getSnippet();
                String title = marker.getTitle();


                if (snippet != null && (snippet.startsWith("http") || snippet.startsWith("https") || snippet.contains(".jpg") || snippet.contains(".png"))){
                    camara cam = (camara) marker.getTag();
                    builder.setMessage("Quieres añadir " + cam.getCameraName() +  " a favoritos?");
                }else{
                    incidencia inc = (incidencia) marker.getTag();
                    builder.setMessage("Quieres añadir " + inc.getIncidenceName() +  " a favoritos?");
                }



                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (snippet != null && (snippet.startsWith("http") || snippet.startsWith("https") || snippet.contains(".jpg") || snippet.contains(".png"))){
                            camara cam = (camara) marker.getTag();
                            ApiService_camara apiServiceCamara = retrofitForCustom.create(ApiService_camara.class);
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", getApplicationContext().MODE_PRIVATE);
                            String token = sharedPreferences.getString("token",null);
                            String ID = sharedPreferences.getString("CurrentLoggedUser",null);


                            String cameraIdValue = cam != null && cam.getCameraId() != null ? cam.getCameraId() : "";
                            String addressValue = cam != null && cam.getAddress() != null ? cam.getAddress() : "no address";
                            String cameraNameValue = cam != null && cam.getCameraName() != null ? cam.getCameraName() : "";
                            String kilometerValue = cam != null && cam.getKilometer() != null ? cam.getKilometer() : "";
                            String latitudeValue = cam != null && cam.getLatitude() != null ? cam.getLatitude() : "";
                            String longitudeValue = cam != null && cam.getLongitude() != null ? cam.getLongitude() : "";
                            String urlValue = cam != null && cam.getUrlImage() != null ? cam.getUrlImage() : "no disponible";
                            apiServiceCamara.addCameraAndFavorite("Bearer " + token,ID,cameraIdValue,addressValue,cameraNameValue,kilometerValue,latitudeValue,longitudeValue,urlValue).enqueue(new Callback<Void>() {

                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        // Handle the successful response (200 OK)
                                        Log.d("API Success", call.request().url().toString());
                                        Log.e("API Errores", call.request().url().toString());
                                    } else {
                                        // Handle other HTTP responses like 4xx or 5xx
                                        Log.e("API Error", "Error Body: " + response.toString());
                                        Log.e("API Error", call.request().url().toString());
                                        Log.e("API Error", call.request().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    // Handle the case where the request failed to execute
                                    Log.e("API Error", "Request failed", t);
                                }
                            });

                        }else{

                            incidencia inc = (incidencia) marker.getTag();
                            ApiService_incidencia apiServiceIncidencia = retrofitForCustom.create(ApiService_incidencia.class);
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", getApplicationContext().MODE_PRIVATE);
                            String token = sharedPreferences.getString("token",null);
                            String ID = sharedPreferences.getString("CurrentLoggedUser",null);


                            String autonomousRegionValue = inc != null && inc.getAutonomousRegion() != null ? inc.getAutonomousRegion() : "no value";
                            String carRegistrationValue = inc != null && inc.getCarRegistration() != null ? inc.getCarRegistration() : "no value";
                            String causeValue = inc != null && inc.getCause() != null ? inc.getCause() : "no value";
                            String cityTownValue = inc != null && inc.getCityTown() != null ? inc.getCityTown() : "no value";
                            String directionValue = inc != null && inc.getDirection() != null ? inc.getDirection() : "no value";
                            String endDateValue = inc != null && inc.getEndDate() != null ? inc.getEndDate() : "no value";
                            String incidenceDescriptionValue = inc != null && inc.getIncidenceDescription() != null ? inc.getIncidenceDescription() : "no value";
                            String incidenceIDValue = inc != null && inc.getIncidenceId() != null ? inc.getIncidenceId() : "no value";
                            String incidenceLevelValue = inc != null && inc.getIncidenceLevel() != null ? inc.getIncidenceLevel() : "no value";


                            apiServiceIncidencia.addIncidenciaAndFavorite("Bearer " + token,ID,autonomousRegionValue,carRegistrationValue,causeValue,cityTownValue,directionValue,endDateValue,incidenceDescriptionValue,incidenceIDValue,incidenceLevelValue).enqueue(new Callback<Void>() {

                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        // Handle the successful response (200 OK)
                                        Log.d("API Success", call.request().url().toString());
                                        Log.e("API Error", call.request().toString());
                                    } else {
                                        // Handle other HTTP responses like 4xx or 5xx
                                        Log.e("API Error", "Error Body: " + response.toString());
                                        Log.e("API Error", call.request().url().toString());
                                        Log.e("API Error", call.request().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    // Handle the case where the request failed to execute
                                    Log.e("API Error", "Request failed", t);
                                }
                            });

                        }


                    }
                });

                // Add "No" button
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });




    }

    private void getSurroundingCameras() {


        ApiService_camara apiServiceCamera = retrofit.create(ApiService_camara.class);

        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // You can use the location here
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    MarkerOptions myLocationMarker =
                    new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title("Mi posición")
                            .snippet("")// Ensure this is not null
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    mMap.addMarker(myLocationMarker);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),10));
                    apiServiceCamera.getCamerasByLocation(latitude,longitude,5).enqueue(new Callback<camararesponse>() {
                        @Override
                        public void onResponse(Call<camararesponse> call, Response<camararesponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<camara> cameras = response.body().getCamaras();
                                for (camara camera : cameras) {

                                   Marker marker =  mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Float.parseFloat(camera.getLatitude()), Float.parseFloat(camera.getLongitude())))
                                            .title(camera.getCameraName() + " Cam : " + camera.getCameraId()) // Ensure this is not null
                                            .snippet(camera.getUrlImage())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); // Custom icon for camera;

                                    if (marker != null) {
                                        marker.setTag(camera);
                                    }
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
            }
        });
    }

    private void getSurroundingIncidencesByDateAndLocation() {

        ApiService_incidencia apiServiceIncidencia = retrofit.create(ApiService_incidencia.class);

        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }


        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        // Split the date into day, month, and year
        String[] dateParts = formattedDate.split("/");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // You can use the location here
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    apiServiceIncidencia.getTrafficIncidencesByDateAndLocation(day,month,year,latitude,longitude,5).enqueue(new Callback<incidenciaresponse>() {
                        @Override
                        public void onResponse(Call<incidenciaresponse> call, Response<incidenciaresponse> response) {
                            if (response.isSuccessful() && response.body() != null) {


                                List<incidencia> incidencias = response.body().getIncidencias();
                                for (incidencia incidencia : incidencias) {
                                    MarkerOptions markerOptions = new MarkerOptions()
                                            .position(new LatLng(Float.parseFloat(incidencia.getLatitude()), Float.parseFloat(incidencia.getLongitude())))
                                            .title(incidencia.getIncidenceType() + " ID: " + incidencia.getIncidenceId()) // Ensure this is not null
                                            .snippet(incidencia.getCause())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)); // Custom icon for incidencia

                                    Marker marker = mMap.addMarker(markerOptions);
                                    if (marker != null) {
                                        marker.setTag(incidencia);
                                    }
                                }

                            } else {
                                // Handle the case where the response was not successful
                                Log.e("API Error", "Response not successful: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<incidenciaresponse> call, Throwable t) {
                            // Handle the case where the request failed to execute
                            Log.e("API Error", "Request failed", t);
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with location retrieval and map operations
                //getLastKnownLocation();
                // Add markers to the map, etc.
            } else {
                // Permission denied, handle it as needed (e.g., show a message to the user)
                Log.e("Permission", "Location permission denied");
            }
        }
    }

}
