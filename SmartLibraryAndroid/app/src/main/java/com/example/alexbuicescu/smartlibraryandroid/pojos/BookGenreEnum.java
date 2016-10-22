package com.example.alexbuicescu.smartlibraryandroid.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexbuicescu on 10/22/16.
 */

public enum BookGenreEnum {
    @SerializedName("SF")
    SCI_FI("Sci-Fi"),

    @SerializedName("Fan")
    FANTASY("Fantasy"),

    @SerializedName("Prog")
    PROGRAMMING("Programming"),

    @SerializedName("SelfDev")
    SELF_DEVELOPMENT("Self Development"),

    @SerializedName("Romance")
    ROMANCE("ROMANCE");

    private String value;

    BookGenreEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
