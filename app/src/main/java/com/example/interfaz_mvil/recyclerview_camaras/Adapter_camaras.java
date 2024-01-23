package com.example.interfaz_mvil.recyclerview_camaras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaz_mvil.R;
import com.example.interfaz_mvil.apistuff_camara.camara;
import com.example.interfaz_mvil.recyclerview_incidencias.ViewHolder_incidencias;

import java.util.List;

public class Adapter_camaras extends RecyclerView.Adapter<ViewHolder_camaras> {
    private List<camara> items;

    public Adapter_camaras(List<camara> items) {
        this.items = items;
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
