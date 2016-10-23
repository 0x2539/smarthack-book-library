package com.example.alexbuicescu.smartlibraryandroid.fcm;

import android.content.Context;
import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.utils.PrefUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;

/**
 *
 * Created by alexbuicescu on Oct 23 - 2016.
 */

public class MyFirebaseUtils {
    private static final String KEY_TOPICS = "KEY_TOPICS";
    private static final String TAG = MyFirebaseUtils.class.getSimpleName();

    public static void subscribeToCurrentUserTopic(Context context, String userId) {
        if (userId != null && !userId.equals("")) {
            subscribeToTopic(context, "user_" + userId);
        }
    }

    public static void subscribeToTopic(Context context, String topic)
    {
        Log.i(TAG, "subscribed to firebase messaging, topic: " + topic);
        HashSet<String> topics = getTopics(context);

        //if the token was not already in the list, then add it
        if (topics.add(topic)) {

            // [START subscribe_topics]
            FirebaseMessaging.getInstance().subscribeToTopic(topic);
            // [END subscribe_topics]

            PrefUtils.setStringToPrefs(context, KEY_TOPICS, new Gson().toJson(topics));
        }
    }

    public static void unsubscribeFromAllTopics(Context context) {
        HashSet<String> topics = getTopics(context);

        for (String topic : topics) {
            // [START subscribe_topics]
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
            // [END subscribe_topics]
        }
        clearTopics(context);
    }

    public static HashSet<String> getTopics(Context context)
    {
        String listJson = PrefUtils.getStringFromPrefs(context, KEY_TOPICS, new Gson().toJson(new HashSet<String>()));
        HashSet<String> topics = new Gson().fromJson(listJson, new TypeToken<HashSet<String>>(){}.getType());
        return topics;
    }

    private static void clearTopics(Context context) {
        PrefUtils.setStringToPrefs(context, KEY_TOPICS, new Gson().toJson(new HashSet<String>()));
    }
}
