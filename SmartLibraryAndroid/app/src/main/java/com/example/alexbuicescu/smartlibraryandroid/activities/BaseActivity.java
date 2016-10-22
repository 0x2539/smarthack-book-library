package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.BaseRestEventBusMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {

        }
    }

    @Subscribe
    public void onEvent(BaseRestEventBusMessage message) {
        if (!message.isSuccess() && message.getErrorMessage() != null) {
            String errorMessage = null;
            if (message.getErrorMessage().equals("LOGIN_INVALID")) {
                errorMessage = "You are not logged in";
            }

            if (message.getErrorMessage().equals("CANT_BORROW")) {
                errorMessage = "You can't borrow anymore books";
            }

            if (errorMessage != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(errorMessage)
                        .setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }
                        );
                builder.show();
            }
        }
    }
}
