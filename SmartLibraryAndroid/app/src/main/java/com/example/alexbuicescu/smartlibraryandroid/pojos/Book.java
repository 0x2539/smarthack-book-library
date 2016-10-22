package com.example.alexbuicescu.smartlibraryandroid.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class Book {
    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
