package com.example.interfaz_mvil.apistuff_users;

import com.example.interfaz_mvil.apistuff_incidencia.incidencia;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class userresponse {

    @SerializedName("users")
    private List<user> users;

    // Getter and Setter

    public List<user> getUsuariosLista() {
        return users;
    }


}
