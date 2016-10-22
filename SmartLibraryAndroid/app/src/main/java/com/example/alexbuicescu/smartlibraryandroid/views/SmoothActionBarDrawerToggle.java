package com.example.alexbuicescu.smartlibraryandroid.views;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

    private AppCompatActivity activity;
    private Runnable runnable;
    private int state;
    private boolean isOpened;

    public SmoothActionBarDrawerToggle(AppCompatActivity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = activity;
        this.state = DrawerLayout.STATE_IDLE;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        activity.supportInvalidateOptionsMenu();
        this.isOpened = true;
    }
    @Override
    public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        activity.supportInvalidateOptionsMenu();
        this.isOpened = false;
    }
    @Override
    public void onDrawerStateChanged(int newState) {
        super.onDrawerStateChanged(newState);
        state = newState;
        if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
            runnable.run();
            runnable = null;
        }
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }
}
