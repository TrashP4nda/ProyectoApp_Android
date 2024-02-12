package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
import com.example.interfaz_mvil.recyclerview_camaras.Adapter_camaras;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Camaras extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_camaras adapterCamaras;
    private List<camara> items;
    private Spinner paginas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }


        setContentView(R.layout.activity_camaras);


        Spinner paginas = findViewById(R.id.camaras_spinner);
        recyclerView = findViewById(R.id.recycler_camaras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.euskadi.eus/traffic/v1.0/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService_camara apiServiceCamera = retrofit.create(ApiService_camara.class);


        adapterCamaras = new Adapter_camaras(this,items);
        recyclerView.setAdapter(adapterCamaras);

        List<String> spinnerData = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        apiServiceCamera.getCameras(1).enqueue(new Callback<camararesponse>() {
            @Override
            public void onResponse(Call<camararesponse> call, Response<camararesponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<camara> cameras = response.body().getCamaras();

                    adapterCamaras.setData(cameras);

                    for (int i = 1; i <= response.body().getTotalPages(); i++) {
                        spinnerData.add(String.valueOf(i));
                    }
                    paginas.setAdapter(adapter);


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


        paginas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                apiServiceCamera.getCameras(position+1).enqueue(new Callback<camararesponse>() {
                    @Override
                    public void onResponse(Call<camararesponse> call, Response<camararesponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<camara> cameras = response.body().getCamaras();

                            adapterCamaras.setData(cameras);
                        }
                    }

                    @Override
                    public void onFailure(Call<camararesponse> call, Throwable t) {
                        // Handle the case where the request failed to execute
                        Log.e("API Error", "Request failed", t);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });
    }





}