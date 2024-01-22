package com.example.interfaz_mvil.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaz_mvil.R;

import java.util.List;

import com.example.interfaz_mvil.apistuff.incidencia;
import com.example.interfaz_mvil.recyclerview.ViewHolder;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private List<incidencia> items;

    public Adapter(List<incidencia> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incidencia ,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        incidencia item = items.get(position);
        holder.textView.setText("ID Incidencia : " + item.getIncidenceId());
    }


    public void setData(List<incidencia> newData) {
        this.items.clear(); // Clear the existing data
        this.items.addAll(newData); // Add new data
        notifyDataSetChanged(); // Notify the adapter about the data change
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
