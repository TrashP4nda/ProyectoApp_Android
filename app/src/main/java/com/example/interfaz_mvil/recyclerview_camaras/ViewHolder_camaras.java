package com.example.interfaz_mvil.recyclerview_camaras;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaz_mvil.R;

public class ViewHolder_camaras extends RecyclerView.ViewHolder {
    TextView textView;

    public ViewHolder_camaras(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textViewItem);
    }
}
