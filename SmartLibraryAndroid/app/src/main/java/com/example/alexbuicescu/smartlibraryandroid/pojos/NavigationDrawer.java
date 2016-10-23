package com.example.alexbuicescu.smartlibraryandroid.pojos;

import android.support.v7.app.AppCompatActivity;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.activities.BorrowedActivity;
import com.example.alexbuicescu.smartlibraryandroid.activities.LoginActivity;
import com.example.alexbuicescu.smartlibraryandroid.activities.MainActivity;
import com.example.alexbuicescu.smartlibraryandroid.activities.ProfileActivity;
import com.example.alexbuicescu.smartlibraryandroid.activities.RecommendedActivity;
import com.example.alexbuicescu.smartlibraryandroid.activities.ScanBookActivity;

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
        aMap.put(R.id.navigation_drawer_menu_borrowed_action, new BorrowedActivity());
        aMap.put(R.id.navigation_drawer_menu_scan_book_action, new ScanBookActivity());
        aMap.put(R.id.navigation_drawer_menu_recommended_action, new RecommendedActivity());
        aMap.put(R.id.navigation_drawer_menu_profile_action, new ProfileActivity());
//        aMap.put(R.id.navigation_drawer_menu_settings_action, new LoginActivity());

        navigationDrawerItemsActivitiesMap = Collections.unmodifiableMap(aMap);
    }

}
