package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.interfaz_mvil.apistuff_camara.ApiService_camara;
import com.example.interfaz_mvil.apistuff_camara.camara;
import com.example.interfaz_mvil.apistuff_camara.camararesponse;
import com.example.interfaz_mvil.apistuff_users.ApiService_usuario;
import com.example.interfaz_mvil.apistuff_users.user;
import com.example.interfaz_mvil.apistuff_users.userresponse;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.boton_login);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5009/api/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create()).build();


        ApiService_usuario apiServiceUsuario = retrofit.create(ApiService_usuario.class);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Principal.class);
                //startActivity(intent);

                apiServiceUsuario.getUsuarios().enqueue(new Callback<List<user>>() {
                    @Override
                    public void onResponse(Call<List<user>> call, Response<List<user>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<user> userList = response.body();
                            Log.e("Response",response.body().toString());
                            // Handle the list of users as needed
                            for (user userItem : userList) {
                                Log.d("User Info", "Username: " + userItem.toString());
                                Log.d("User Info", "Email: " + userItem.getEmail());
                                // Perform any other processing you need here
                            }
                        } else {
                            Log.e("API Error", "Response not successful: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<user>> call, Throwable t) {
                        Log.e("API Error", "Request failed", t);
                    }
                });


            }
        });
    }
}