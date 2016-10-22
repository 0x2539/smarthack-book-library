package com.example.alexbuicescu.smartlibraryandroid.utils;

import android.content.Context;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class UserPreferences {
    private static final String KEY_LOGIN_TOKEN = "KEY_LOGIN_TOKEN";

    public static void setLoginToken(Context context, String token) {
        PrefUtils.setStringToPrefs(context, KEY_LOGIN_TOKEN, token);
    }

    public static String getLoginToken(Context context) {
        return PrefUtils.getStringFromPrefs(context, KEY_LOGIN_TOKEN, "");
    }

    public static boolean isUserLoggedIn(Context context) {
        return !PrefUtils.getStringFromPrefs(context, KEY_LOGIN_TOKEN, "").equals("");
    }
}
