package com.example.interfaz_mvil.recyclerview_camaras;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaz_mvil.R;
import com.example.interfaz_mvil.apistuff_camara.camara;
import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.example.interfaz_mvil.recyclerview_incidencias.Incidencias_Inside;
import com.example.interfaz_mvil.recyclerview_incidencias.ViewHolder_incidencias;

import java.util.List;

public class Adapter_camaras extends RecyclerView.Adapter<ViewHolder_camaras> {
    private List<camara> items;
    private Context context;

    public Adapter_camaras(Context context,List<camara> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder_camaras onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camara ,parent, false);
        return new ViewHolder_camaras(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder_camaras holder, int position) {
        camara item = items.get(position);
        holder.textView.setText("ID Camara : " + item.getCameraId());

        camara cam = items.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Camaras_Inside.class);
                intent.putExtra("camaraID",cam.getCameraId());
                intent.putExtra("cameraName",cam.getCameraName());
                intent.putExtra("address",cam.getAddress());
                intent.putExtra("url",cam.getUrlImage());
                context.startActivity(intent);
            }
        });
    }


    public void setData(List<camara> newData) {
        this.items.clear(); // Clear the existing data
        this.items.addAll(newData); // Add new data
        notifyDataSetChanged(); // Notify the adapter about the data change
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
