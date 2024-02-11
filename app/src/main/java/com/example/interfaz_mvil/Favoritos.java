package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Favoritos extends AppCompatActivity {
    private Button camaras,incidencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        camaras = findViewById(R.id.camarasfavoritas);
        incidencias =findViewById(R.id.incidenciasfavoritas);



        camaras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CamarasFavoritas.class);
                startActivity(intent);
            }
        });


        incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncidenciasFavoritas.class);
                startActivity(intent);
            }
        });
    }
}