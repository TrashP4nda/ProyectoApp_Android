package com.example.interfaz_mvil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.interfaz_mvil.mapa.MapsActivity;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Titulito");
        toolbar.setNavigationIcon(R.drawable.ic_launcher_foreground);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Check which menu item was clicked based on its ID
        if (id == R.id.item1) {

            return true;
        } else if (id == R.id.item2) {
            Intent intent = new Intent(getApplicationContext(),Incidencias.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.item3) {
            Intent intent = new Intent(getApplicationContext(),Camaras.class);
            startActivity(intent);
            return true;}
        else if (id == R.id.item4) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
            return true;}
        // Add more conditions for other menu items as needed

        return super.onOptionsItemSelected(item);
}}