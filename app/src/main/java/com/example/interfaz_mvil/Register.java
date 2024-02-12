package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.interfaz_mvil.apistuff_camara.ApiService_camara;
import com.example.interfaz_mvil.apistuff_users.ApiService_usuario;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    TextInputEditText username,email;
    EditText password;

    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }

        setContentView(R.layout.activity_register);


        username = findViewById(R.id.register_username);
        password = findViewById(R.id.password_regsister);
        email = findViewById(R.id.register_email);
        registrar = findViewById(R.id.registrar);


        Retrofit retrofitForCustom = new Retrofit.Builder().baseUrl("http://192.168.1.136:5009/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService_usuario apiServiceUsuario = retrofitForCustom.create(ApiService_usuario.class);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceUsuario.registerUser( username.getText().toString(),password.getText().toString(),email.getText().toString()).enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            finish();
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
        });






    }
}