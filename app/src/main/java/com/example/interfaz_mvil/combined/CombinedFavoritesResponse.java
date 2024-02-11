package com.example.interfaz_mvil.combined;

import com.example.interfaz_mvil.apistuff_camara.camara;
import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.google.gson.annotations.SerializedName;
import java.util.List;

// Main response class
public class CombinedFavoritesResponse {

    @SerializedName("favoriteCameras")
    private List<camara> favoriteCameras;

    @SerializedName("favoriteIncidencias")
    private List<incidencia> favoriteIncidencias;

    // Getters
    public List<camara> getFavoriteCameras() {
        return favoriteCameras;
    }

    public List<incidencia> getFavoriteIncidencias() {
        return favoriteIncidencias;
    }

    // Setters
    public void setFavoriteCameras(List<camara> favoriteCameras) {
        this.favoriteCameras = favoriteCameras;
    }

    public void setFavoriteIncidencias(List<incidencia> favoriteIncidencias) {
        this.favoriteIncidencias = favoriteIncidencias;
    }
}
