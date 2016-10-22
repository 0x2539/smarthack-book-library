package com.example.alexbuicescu.smartlibraryandroid.rest.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */

public class LoginRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("googleUserId")
    private String googleUserId;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public LoginRequest(String email, String password, String googleUserId) {
        this.email = email;
        this.password = password;
        this.googleUserId = googleUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }
}
