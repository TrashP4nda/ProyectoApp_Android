package com.example.interfaz_mvil.apistuff_camara;

import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class camararesponse {

    @SerializedName("cameras")
    private List<camara> cameras;

    // Getter and Setter

    public List<camara> getCamaras() {
        return cameras;
    }

    @SerializedName("totalPages")
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }
}
