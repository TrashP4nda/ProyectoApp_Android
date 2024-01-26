package com.example.interfaz_mvil.apistuff_users;

import com.google.gson.annotations.SerializedName;

public class user {

    public String getId() {
        return id;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String Username;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @SerializedName("passwordHash")
    private String PasswordHash;

    @SerializedName("email")
    private String Email;



}
