package com.example.interfaz_mvil.recyclerview_incidencias;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaz_mvil.R;

import java.util.List;

import com.example.interfaz_mvil.apistuff_incidencia.incidencia;

public class Adapter_incidencias extends RecyclerView.Adapter<ViewHolder_incidencias> {
    private List<incidencia> items;
    private Context context;
    public Adapter_incidencias(Context context,List<incidencia> items) {
        this.items = items; this.context=context;
    }

    @Override
    public ViewHolder_incidencias onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incidencia ,parent, false);
        return new ViewHolder_incidencias(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder_incidencias holder, int position) {
        incidencia item = items.get(position);
        holder.textView.setText("ID Incidencia : " + item.getIncidenceId());
        incidencia inc = items.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Incidencias_Inside.class);
                intent.putExtra("titulo" , inc.getCause());
                intent.putExtra("town",inc.getCityTown());
                intent.putExtra("autonomous",inc.getAutonomousRegion());
                intent.putExtra("direction",inc.getDirection());
                intent.putExtra("description",inc.getIncidenceDescription());
                context.startActivity(intent);
            }
        });
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
