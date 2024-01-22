package com.example.interfaz_mvil.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.interfaz_mvil.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public ViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textViewItem);
    }
}
