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

import com.example.interfaz_mvil.apistuff_incidencia.ApiService_incidencia;
import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.example.interfaz_mvil.apistuff_incidencia.incidenciaresponse;
import com.example.interfaz_mvil.recyclerview_incidencias.Adapter_incidencias;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Incidencias extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_incidencias adapterIncidencias;
    private List<incidencia> items;

    private Spinner paginas;
    private Button filtrarporfechas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }


        setContentView(R.layout.activity_incidencias);

        Spinner paginas = findViewById(R.id.spinner_paginas);
        filtrarporfechas = findViewById(R.id.filtrarporfecha);
        recyclerView = findViewById(R.id.recycler_incidencias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();



        List<String> spinnerData = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.euskadi.eus/traffic/v1.0/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService_incidencia apiServiceIncidencia = retrofit.create(ApiService_incidencia.class);


        adapterIncidencias = new Adapter_incidencias(this,items);
        recyclerView.setAdapter(adapterIncidencias);

       apiServiceIncidencia.getTrafficIncidences(1).enqueue(new Callback<incidenciaresponse>() {
            @Override
            public void onResponse(Call<incidenciaresponse> call, Response<incidenciaresponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Assuming incidenciaresponse has a method getIncidencias() that returns a List<Incidencia>
                    List<incidencia> incidences = response.body().getIncidencias();

                   adapterIncidencias.setData(incidences);

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
            public void onFailure(Call<incidenciaresponse> call, Throwable t) {
                // Handle the case where the request failed to execute
                Log.e("API Error", "Request failed", t);
            }
        });



        // Add items to the list

        filtrarporfechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                DatePickerDialog datePickerDialog = new DatePickerDialog(Incidencias.this
                        ,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                apiServiceIncidencia.getTrafficIncidencesByDate(String.valueOf(dayOfMonth),String.valueOf(month),String.valueOf(year)).enqueue(new Callback<incidenciaresponse>() {
                                    @Override
                                    public void onResponse(Call<incidenciaresponse> call, Response<incidenciaresponse> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            // Assuming incidenciaresponse has a method getIncidencias() that returns a List<Incidencia>
                                            List<incidencia> incidences = response.body().getIncidencias();

                                            adapterIncidencias.setData(incidences);


                                        } else {
                                            // Handle the case where the response was not successful
                                            Log.e("API Error", "Response not successful: " + call.request().url());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<incidenciaresponse> call, Throwable t) {
                                        // Handle the case where the request failed to execute
                                        Log.e("API Error", "Request failed", t);
                                    }
                                });
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });




        paginas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                apiServiceIncidencia.getTrafficIncidences(position+1).enqueue(new Callback<incidenciaresponse>() {
                    @Override
                    public void onResponse(Call<incidenciaresponse> call, Response<incidenciaresponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Assuming incidenciaresponse has a method getIncidencias() that returns a List<Incidencia>
                            List<incidencia> incidences = response.body().getIncidencias();

                            adapterIncidencias.setData(incidences);
                            Log.e("asdasdawdas","asdasdasdasdFASHFOASFOAISNJFNOSANM");


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

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });



    }





}