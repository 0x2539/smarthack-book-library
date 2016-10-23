package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.example.alexbuicescu.smartlibraryandroid.managers.ProfileManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.ProfileMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;

import org.greenrobot.eventbus.Subscribe;

public class ProfileActivity extends BaseActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initLayout();
        loadBooks();
    }

    private void loadBooks() {
        RestClient.getInstance(ProfileActivity.this).PROFILE_CALL();
    }

    private void initLayout() {
        initToolbar();

        firstNameEditText = (EditText) findViewById(R.id.activity_profile_firstname_edittext);
        lastNameEditText = (EditText) findViewById(R.id.activity_profile_lastname_edittext);
        emailEditText = (EditText) findViewById(R.id.activity_profile_email_edittext);
    }

    private void initToolbar() {
        try {
            ((AppCompatActivity) ProfileActivity.this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) ProfileActivity.this).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) ProfileActivity.this).getSupportActionBar().setTitle("Profile");
        } catch (Exception e) {

        }
    }

    private void save() {
        RestClient.getInstance().UPDATE_PROFILE_CALL(
                firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(),
                emailEditText.getText().toString()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.menu_profile_save_action) {
            save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(ProfileMessage message) {
        if (message.isSuccess()) {
            firstNameEditText.setText(ProfileManager.getInstance().getFirstName());
            lastNameEditText.setText(ProfileManager.getInstance().getLastName());
            emailEditText.setText(ProfileManager.getInstance().getEmail());
        }
    }
}
