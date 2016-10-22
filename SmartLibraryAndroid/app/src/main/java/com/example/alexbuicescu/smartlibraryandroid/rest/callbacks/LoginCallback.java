package com.example.alexbuicescu.smartlibraryandroid.rest.callbacks;

import android.content.Context;

import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.LoginMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.LoginRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoginResponse;
import com.example.alexbuicescu.smartlibraryandroid.utils.UserPreferences;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class LoginCallback extends HttpCallbackCustom<LoginRequest, LoginResponse> {

    private final String TAG = LoginCallback.class.getName();

    public LoginCallback(Context context, LoginRequest request) {
        super(context, request);
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

        if (onResponseBase(response)) {
            UserPreferences.setLoginToken(getContext(), response.body().getToken());

            EventBus.getDefault().post(new LoginMessage(true));
        } else {
            EventBus.getDefault().post(new LoginMessage(false));
        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {

        onFailureBase(t, getEventBusMessage());
    }

    private LoginMessage getEventBusMessage() {
        return new LoginMessage();
    }
}