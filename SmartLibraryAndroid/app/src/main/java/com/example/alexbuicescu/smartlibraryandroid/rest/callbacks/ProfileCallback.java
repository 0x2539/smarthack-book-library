package com.example.alexbuicescu.smartlibraryandroid.rest.callbacks;

import android.content.Context;
import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.managers.ProfileManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.ProfileMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.ProfileResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class ProfileCallback extends HttpCallbackCustom<EmptyRequest, ProfileResponse> {

    private final String TAG = ProfileCallback.class.getSimpleName();

    public ProfileCallback(Context context, EmptyRequest request) {
        super(context, request);
    }

    @Override
    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

        if (onResponseBase(response)) {
            Log.i(TAG, "onResponse: sending true");
            ProfileManager.getInstance().setFirstName(response.body().getFirstName());
            ProfileManager.getInstance().setLastName(response.body().getLastName());
            ProfileManager.getInstance().setEmail(response.body().getEmail());
            EventBus.getDefault().post(new ProfileMessage(true));
        } else {
            Log.i(TAG, "onResponse: sending false");
            EventBus.getDefault().post(new ProfileMessage(false));
        }
    }

    @Override
    public void onFailure(Call<ProfileResponse> call, Throwable t) {

        onFailureBase(t, getEventBusMessage());
    }

    private ProfileMessage getEventBusMessage() {
        return new ProfileMessage();
    }
}