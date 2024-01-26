package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.interfaz_mvil.tokenApi.*;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private Button boton;
    private TextInputEditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.boton_login);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.password_login);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.134:5009/api/") // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create()).build();


        ApiService_token apiServiceToken = retrofit.create(ApiService_token.class);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Principal.class);
                //startActivity(intent);

                apiServiceToken.Login(username.getText().toString(),password.getText().toString()).enqueue(new Callback<tokenresponse>() {
                    @Override
                    public void onResponse(Call<tokenresponse> call, Response<tokenresponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            tokenresponse tokenResponse = response.body();
                            user userObject = tokenResponse.getUserObject();

                            if (tokenResponse.getToken() != null && userObject != null) {
                                String token = tokenResponse.getToken();



                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", getApplicationContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", token);
                                editor.putString("CurrentLoggedUser", userObject.getId());
                                editor.apply();


                                Intent intent = new Intent(getApplicationContext(),Principal.class);
                                intent.putExtra("UserID", userObject.getId());
                                intent.putExtra("Username", userObject.getUsername());
                                startActivity(intent);


                            }
                        } else {
                            Log.e("API Error","Response not successful: " + response.code() + " " + call.request());

                        }
                    }

                    @Override
                    public void onFailure(Call<tokenresponse> call, Throwable t) {
                        Log.e("API Error", "Request failed: " + t.getMessage(), t);
                    }

                });


            }
        });
    }
}