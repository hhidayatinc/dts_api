package com.example.profile.model;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNoHp() {
        return noHp;
    }

    public Profile(int id, String nama, String email, String noHp) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noHp = noHp;
    }

    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;
    @SerializedName("noHp")
    private String noHp;
}
