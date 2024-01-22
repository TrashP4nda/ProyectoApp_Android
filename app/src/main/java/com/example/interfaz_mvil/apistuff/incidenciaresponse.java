package com.example.interfaz_mvil.apistuff;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class incidenciaresponse {

    @SerializedName("incidences")
    private List<incidencia> incidences;

    // Getter and Setter

    public List<incidencia> getIncidencias() {
        return incidences;
    }
}
