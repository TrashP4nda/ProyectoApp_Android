package com.example.interfaz_mvil.recyclerview_incidencias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.interfaz_mvil.R;

public class Incidencias_Inside extends AppCompatActivity {

    private TextView titulo,descripcion,town,autonomous,direction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }


        setContentView(R.layout.activity_incidencias_inside);
        Intent intent = getIntent();

        titulo = findViewById(R.id.tituloincidencia);
        descripcion = findViewById(R.id.description);
        town = findViewById(R.id.town);
        autonomous = findViewById(R.id.autonomous);
        direction = findViewById(R.id.direction);

        titulo.setText("Titulo : " + intent.getStringExtra("titulo"));
        town.setText("Ciudad : " +intent.getStringExtra("town"));
        autonomous.setText("Comunidad Autónoma : " +intent.getStringExtra("autonomous"));
        direction.setText("Dirección : " + intent.getStringExtra("direction"));
        if (intent.getStringExtra("description") != null){
        descripcion.setText("Descripción : " + intent.getStringExtra("description"));}else{
            descripcion.setText("Descripción : " +"no disponible");
        }




    }
}