package com.example.alexbuicescu.smartlibraryandroid.fcm;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by alexbuicescu on Oct 23 - 2016.
 */
public enum FirebaseActionType {

    @SerializedName("1")
    BOOK_DUE_SOON(1);

    private int backendValue;

    FirebaseActionType(final int backendValue) {
        this.backendValue = backendValue;
    }

    public static FirebaseActionType getEnumById(int backendId) {
        for (FirebaseActionType e : FirebaseActionType.values()) {
            if (backendId == e.backendValue) return e;
        }
        return null;
    }

    public static FirebaseActionType getEnumById(String backendIdString) {
        int backendId = 0;
        try {
            backendId = Integer.parseInt(backendIdString);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for (FirebaseActionType e : FirebaseActionType.values()) {
            if (backendId == e.backendValue) return e;
        }
        return null;
    }

    public int getValue() {
        return backendValue;
    }
}
