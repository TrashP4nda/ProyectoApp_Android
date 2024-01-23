package com.example.interfaz_mvil.apistuff_incidencia;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class incidenciaresponse {

    @SerializedName("incidences")
    private List<incidencia> incidences;

    // Getter and Setter

    public List<incidencia> getIncidencias() {
        return incidences;
    }

    @SerializedName("totalPages")
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }
}
