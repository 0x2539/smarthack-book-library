package com.example.alexbuicescu.smartlibraryandroid.rest.callbacks;

import android.content.Context;
import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.LoanDateMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoanDateResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class LoanDateCallback extends HttpCallbackCustom<EmptyRequest, LoanDateResponse> {

    private final String TAG = LoanDateCallback.class.getSimpleName();

    public LoanDateCallback(Context context, EmptyRequest request) {
        super(context, request);
    }

    @Override
    public void onResponse(Call<LoanDateResponse> call, Response<LoanDateResponse> response) {

        if (onResponseBase(response)) {
            Log.i(TAG, "onResponse: sending true");
            EventBus.getDefault().post(new LoanDateMessage(response.body().getDate()));
        } else {
            Log.i(TAG, "onResponse: sending false");
            EventBus.getDefault().post(new LoanDateMessage(false));
        }
    }

    @Override
    public void onFailure(Call<LoanDateResponse> call, Throwable t) {

        onFailureBase(t, getEventBusMessage());
    }

    private LoanDateMessage getEventBusMessage() {
        return new LoanDateMessage();
    }
}