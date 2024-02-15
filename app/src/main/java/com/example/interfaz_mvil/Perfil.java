package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

    private TextView Username;
    private Button editar;
    private user user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }



        setContentView(R.layout.activity_perfil);

        email = findViewById(R.id.emailedit);

        Username = findViewById(R.id.NombreUsuario);
        editar= findViewById(R.id.changesButton);

        Intent intent = getIntent();

        String ID = intent.getStringExtra("UserID");


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.137.1:5009/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();


        ApiService_usuario apiServiceToken = retrofit.create(ApiService_usuario.class);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", getApplicationContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        apiServiceToken.getUserByID("Bearer " + token, ID).enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user = response.body();
                    Username.setText(user.getUsername());

                    email.setText(user.getEmail());


                } else {
                    Log.e("API Error","Response not successful: " + response.code() + " " + call.request());
                }
            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                Log.e("API Error", "Request failed: " + t.getMessage(), t);
            }

        });




        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceToken.editUser("Bearer " + token, user.getId(),user.getId(), user.getUsername(),user.getPasswordHash().toString(),email.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                        finish();

                        } else {
                            Log.e("API Error","Response not successful: " + response.code() + " " + call.request());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("API Error", "Request failed: " + t.getMessage(), t);
                    }

                });
            }
        });

    }
}