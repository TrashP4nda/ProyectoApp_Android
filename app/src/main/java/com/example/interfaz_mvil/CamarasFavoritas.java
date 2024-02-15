package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.interfaz_mvil.apistuff_camara.ApiService_camara;
import com.example.interfaz_mvil.apistuff_camara.camara;

import com.example.interfaz_mvil.apistuff_camara.camararesponse;
import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.example.interfaz_mvil.apistuff_incidencia.incidenciaresponse;
import com.example.interfaz_mvil.combined.CombinedFavoritesResponse;
import com.example.interfaz_mvil.recyclerview_camaras.Adapter_camaras;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CamarasFavoritas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_camaras adapterCamaras;
    private List<camara> items;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }



        setContentView(R.layout.activity_camaras_favoritas);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", getApplicationContext().MODE_PRIVATE);
        id = sharedPreferences.getString("CurrentLoggedUser",null);
        recyclerView = findViewById(R.id.recyclercamfav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.1:5009/api/usuarios/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService_camara apiServiceCamera = retrofit.create(ApiService_camara.class);


        adapterCamaras = new Adapter_camaras(this,items);
        recyclerView.setAdapter(adapterCamaras);

        String token = sharedPreferences.getString("token",null);
        Call<CombinedFavoritesResponse> call = apiServiceCamera.getCombinedFavorites("Bearer " + token,id);
        call.enqueue(new Callback<CombinedFavoritesResponse>() {
            @Override
            public void onResponse(Call<CombinedFavoritesResponse> call, Response<CombinedFavoritesResponse> response) {
                if (response.isSuccessful()) {
                    CombinedFavoritesResponse combinedResponse = response.body();
                    List<camara> favoriteCameras = combinedResponse.getFavoriteCameras();


                    adapterCamaras.setData(favoriteCameras);

                } else {
                    // Handle the error
                }
            }

            @Override
            public void onFailure(Call<CombinedFavoritesResponse> call, Throwable t) {
                // Handle failure
            }
        });



    }





}