package com.example.alexbuicescu.smartlibraryandroid.utils;

import android.content.Context;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class UserPreferences {
    private static final String KEY_LOGIN_TOKEN = "KEY_LOGIN_TOKEN";
    private static final String KEY_USERNAME = "KEY_USERNAME";
    private static final String KEY_USER_ID = "KEY_USER_ID";

    public static void setUserId(Context context, long userId) {
        PrefUtils.setLongToPrefs(context, KEY_USER_ID, userId);
    }

    public static long getUserId(Context context) {
        return PrefUtils.getLongFromPrefs(context, KEY_USER_ID, -1);
    }

    public static void setUsername(Context context, String username) {
        PrefUtils.setStringToPrefs(context, KEY_USERNAME, username);
    }

    public static String getUsername(Context context) {
        return PrefUtils.getStringFromPrefs(context, KEY_USERNAME, "");
    }

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
