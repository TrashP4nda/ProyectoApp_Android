package com.example.interfaz_mvil.recyclerview_camaras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.interfaz_mvil.R;

public class Camaras_Inside extends AppCompatActivity {
    private TextView id,name,address;
    private ImageView url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            getWindow().setAttributes(layoutParams);
        }


        setContentView(R.layout.activity_camaras_inside);
        Intent intent = getIntent();

        id = findViewById(R.id.camid);
        name = findViewById(R.id.camName);
        address = findViewById(R.id.camAddress);
        url = findViewById(R.id.imageView);

        if (intent.getStringExtra("url") != null){
        Glide.with(this)
                .load(intent.getStringExtra("url")).placeholder(R.drawable.no_disponible).fallback(R.drawable.no_disponible)
                .into(url);
         }else{
            Log.e("error","es nulo");
            Glide.with(this)
                    .load(R.drawable.no_disponible)
                    .into(url);
        }

        id.setText("ID de cámara : " + intent.getStringExtra("camaraID"));
        name.setText("Nombre de cámara : " + intent.getStringExtra("cameraName"));
        address.setText("Dirección de cámara : " + intent.getStringExtra("address"));






    }


}