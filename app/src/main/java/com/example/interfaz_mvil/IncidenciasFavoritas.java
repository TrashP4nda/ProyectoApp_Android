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
import android.widget.DatePicker;

import com.example.interfaz_mvil.R;
import com.example.interfaz_mvil.apistuff_camara.ApiService_camara;
import com.example.interfaz_mvil.apistuff_camara.camara;
import com.example.interfaz_mvil.apistuff_incidencia.ApiService_incidencia;
import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.example.interfaz_mvil.apistuff_incidencia.incidenciaresponse;
import com.example.interfaz_mvil.combined.CombinedFavoritesResponse;
import com.example.interfaz_mvil.recyclerview_camaras.Adapter_camaras;
import com.example.interfaz_mvil.recyclerview_incidencias.Adapter_incidencias;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IncidenciasFavoritas extends AppCompatActivity {
    private RecyclerView favoritas;
    private Adapter_incidencias adapterIncidencias;
    private List<incidencia> items;

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }



        setContentView(R.layout.activity_incidencias_favoritas);

        favoritas = findViewById(R.id.recyclerIncidenciasFavoritas);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", getApplicationContext().MODE_PRIVATE);
        id = sharedPreferences.getString("CurrentLoggedUser",null);

        favoritas.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();



        List<String> spinnerData = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);





        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.136:5009/api/usuarios/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService_camara apiServiceCamera = retrofit.create(ApiService_camara.class);


        adapterIncidencias = new Adapter_incidencias(this,items);
        favoritas.setAdapter(adapterIncidencias);

        String token = sharedPreferences.getString("token",null);
        Call<CombinedFavoritesResponse> call = apiServiceCamera.getCombinedFavorites("Bearer " + token,id);
        call.enqueue(new Callback<CombinedFavoritesResponse>() {
            @Override
            public void onResponse(Call<CombinedFavoritesResponse> call, Response<CombinedFavoritesResponse> response) {
                if (response.isSuccessful()) {
                    CombinedFavoritesResponse combinedResponse = response.body();
                    List<incidencia> favoriteIncidencias = combinedResponse.getFavoriteIncidencias();

                    adapterIncidencias.setData(favoriteIncidencias);

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