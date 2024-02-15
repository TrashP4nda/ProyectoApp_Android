package com.example.interfaz_mvil.apistuff_customIncidencia;

import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class customIncidenciaResponse {

    @SerializedName("customIncidences")
    private List<customIncidencia> incidences;

    // Getter and Setter

    public List<customIncidencia> getIncidenciasCustom() {
        return incidences;
    }


}
