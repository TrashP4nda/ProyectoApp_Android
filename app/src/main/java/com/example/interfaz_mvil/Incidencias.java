package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.interfaz_mvil.apistuff.ApiService;
import com.example.interfaz_mvil.apistuff.incidencia;
import com.example.interfaz_mvil.apistuff.incidenciaresponse;
import com.example.interfaz_mvil.recyclerview.Adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Incidencias extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias);



        recyclerView = findViewById(R.id.recycler_incidencias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.euskadi.eus/traffic/v1.0/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);



        apiService.getTrafficIncidences().enqueue(new Callback<incidenciaresponse>() {
            @Override
            public void onResponse(Call<incidenciaresponse> call, Response<incidenciaresponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Assuming incidenciaresponse has a method getIncidencias() that returns a List<Incidencia>
                    List<incidencia> incidences = response.body().getIncidencias();

                    for (incidencia incidence : incidences) {
                        items.add( "Incidencia :" + incidence.getIncidenceId().toString());
                        Log.e("Incidencia",incidence.getIncidenceId().toString() );
                    }


                    adapter = new Adapter(items);
                    recyclerView.setAdapter(adapter);

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






    }
}