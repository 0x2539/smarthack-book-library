package com.example.alexbuicescu.smartlibraryandroid.rest.callbacks;

import android.content.Context;
import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.managers.ProfileManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.UpdateProfileMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.UpdateProfileRequest;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class UpdateProfileCallback extends HttpCallbackCustom<UpdateProfileRequest, EmptyRequest> {

    private final String TAG = UpdateProfileCallback.class.getSimpleName();

    public UpdateProfileCallback(Context context, UpdateProfileRequest request) {
        super(context, request);
    }

    @Override
    public void onResponse(Call<EmptyRequest> call, Response<EmptyRequest> response) {

        if (onResponseBase(response)) {
            Log.i(TAG, "onResponse: sending true");
            EventBus.getDefault().post(new UpdateProfileMessage(true));
        } else {
            Log.i(TAG, "onResponse: sending false");
            EventBus.getDefault().post(new UpdateProfileMessage(false));
        }
    }

    @Override
    public void onFailure(Call<EmptyRequest> call, Throwable t) {

        onFailureBase(t, getEventBusMessage());
    }

    private UpdateProfileMessage getEventBusMessage() {
        return new UpdateProfileMessage();
    }
}