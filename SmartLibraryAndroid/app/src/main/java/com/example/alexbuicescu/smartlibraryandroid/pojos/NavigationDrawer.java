package com.example.alexbuicescu.smartlibraryandroid.pojos;

import android.support.v7.app.AppCompatActivity;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.activities.LoginActivity;
import com.example.alexbuicescu.smartlibraryandroid.activities.MainActivity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public abstract class NavigationDrawer {

    public static final Map<Integer, AppCompatActivity> navigationDrawerItemsActivitiesMap;
    static {
        Map<Integer, AppCompatActivity> aMap = new HashMap<>();

        aMap.put(R.id.navigation_drawer_menu_main_action, new MainActivity());
        aMap.put(R.id.navigation_drawer_menu_borrowed_action, new LoginActivity());
        aMap.put(R.id.navigation_drawer_menu_recommended_action, new LoginActivity());
        aMap.put(R.id.navigation_drawer_menu_profile_action, new LoginActivity());
        aMap.put(R.id.navigation_drawer_menu_list_view, new LoginActivity());

        navigationDrawerItemsActivitiesMap = Collections.unmodifiableMap(aMap);
    }

}
