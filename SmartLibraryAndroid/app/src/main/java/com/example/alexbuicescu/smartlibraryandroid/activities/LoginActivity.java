package com.example.alexbuicescu.smartlibraryandroid.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexbuicescu.smartlibraryandroid.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

public class LoginActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 99;

    private GoogleApiClient mGoogleApiClient;
    
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button googleLoginButton;
    private Button LoginButton;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();
        initSocialMediaLogin();
    }

    private void initLayout() {
        emailEditText = (EditText) findViewById(R.id.activity_login_email_edittext);
        passwordEditText = (EditText) findViewById(R.id.activity_login_password_edittext);

        googleLoginButton = (Button) findViewById(R.id.activity_login_google_button);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        final Drawable googleDrawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_google_24dp, null);
        googleLoginButton.setCompoundDrawablesWithIntrinsicBounds(googleDrawable, null, null, null);

        LoginButton = (Button) findViewById(R.id.activity_login_login_button);
    }

    private void initSocialMediaLogin() {
        new Thread(new Runnable() {
            public void run() {
                googleSignIn();
            }
        }).start();
    }

    private void googleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar"))
                .requestEmail()
                .requestProfile()
//                .requestServerAuthCode(getString(R.string.google_server_oauth2_client_id))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .enableAutoManage(
                        LoginActivity.this,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                googleConnectionHandler(false);
                            }
                        }
                )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        googleConnectionHandler(true);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        googleConnectionHandler(false);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void googleConnectionHandler(boolean success) {

        if (!success) {
            Toast.makeText(LoginActivity.this, "Failed to connect to google", Toast.LENGTH_SHORT).show();
        }
        else {
            //get google id here and send to backend
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            String userId = result.getSignInAccount().getId();
            Log.i(TAG, "handleSignInResult google login id: " + userId);
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(LoginActivity.this, "Failed to login with google", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
}
