package com.example.interfaz_mvil.recyclerview_incidencias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.interfaz_mvil.R;

public class Incidencias_Inside extends AppCompatActivity {

    private TextView titulo,descripcion,town,autonomous,direction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias_inside);
        Intent intent = getIntent();

        titulo = findViewById(R.id.tituloincidencia);
        descripcion = findViewById(R.id.description);
        town = findViewById(R.id.town);
        autonomous = findViewById(R.id.autonomous);
        direction = findViewById(R.id.direction);

        titulo.setText(intent.getStringExtra("titulo"));
        town.setText(intent.getStringExtra("town"));
        autonomous.setText(intent.getStringExtra("autonomous"));
        direction.setText(intent.getStringExtra("direction"));
        if (intent.getStringExtra("description") != null){
        descripcion.setText(intent.getStringExtra("description"));}else{
            descripcion.setText("no disponible");
        }




    }
}