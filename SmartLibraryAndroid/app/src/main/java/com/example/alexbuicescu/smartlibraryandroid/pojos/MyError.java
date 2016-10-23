package com.example.alexbuicescu.smartlibraryandroid.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class MyError {
    @SerializedName("error")
    private String error;

    public String
    getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
