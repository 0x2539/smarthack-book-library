package com.example.alexbuicescu.smartlibraryandroid.rest.responses;

import com.example.alexbuicescu.smartlibraryandroid.pojos.Profile;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class ProfileResponse {

    @SerializedName("model")
    private String model;

    @SerializedName("pk")
    private String userId;

    @SerializedName("fields")
    private Profile profile;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
