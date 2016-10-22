package com.example.alexbuicescu.smartlibraryandroid;

import android.app.Application;

import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class SmartLibraryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RestClient.getInstance(getApplicationContext());
    }
}
