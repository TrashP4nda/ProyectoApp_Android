package com.example.interfaz_mvil.recyclerview_incidencias;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaz_mvil.R;

public class ViewHolder_incidencias extends RecyclerView.ViewHolder {
    TextView textView;

    public ViewHolder_incidencias(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textViewItem);
    }
}
