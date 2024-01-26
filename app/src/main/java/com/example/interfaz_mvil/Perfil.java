package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.interfaz_mvil.apistuff_users.ApiService_usuario;
import com.example.interfaz_mvil.apistuff_users.user;
import com.example.interfaz_mvil.tokenApi.ApiService_token;
import com.example.interfaz_mvil.tokenApi.tokenresponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Perfil extends AppCompatActivity {

    private TextInputEditText email;
    private EditText pasguord;
    private TextView Username;
    private Button editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        email = findViewById(R.id.emailedit);
        pasguord = findViewById(R.id.editPassword);
        Username = findViewById(R.id.NombreUsuario);
        editar= findViewById(R.id.changesButton);

        Intent intent = getIntent();

        String ID = intent.getStringExtra("UserID");


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.134:5009/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();


        ApiService_usuario apiServiceToken = retrofit.create(ApiService_usuario.class);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", getApplicationContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        apiServiceToken.getUserByID("Bearer " + token, ID).enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user usuario = response.body();
                    Username.setText(usuario.getUsername());
                    pasguord.setText(usuario.getPasswordHash());
                    email.setText(usuario.getEmail());


                } else {
                    Log.e("API Error", "Error");
                }
            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                Log.e("API Error", "Request failed: " + t.getMessage(), t);
            }

        });


        apiServiceToken.getUserByID("Bearer " + token, ID).enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user usuario = response.body();
                    Username.setText(usuario.getUsername());
                    pasguord.setText(usuario.getPasswordHash());
                    email.setText(usuario.getEmail());


                } else {
                    Log.e("API Error", "Error");
                }
            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                Log.e("API Error", "Request failed: " + t.getMessage(), t);
            }

        });




    }
}