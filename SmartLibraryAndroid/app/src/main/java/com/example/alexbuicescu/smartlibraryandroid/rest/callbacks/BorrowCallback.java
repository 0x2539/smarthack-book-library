package com.example.alexbuicescu.smartlibraryandroid.rest.callbacks;

import android.content.Context;
import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.managers.BooksManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.BorrowMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class BorrowCallback extends HttpCallbackCustom<EmptyRequest, EmptyRequest> {

    private final String TAG = BorrowCallback.class.getSimpleName();

    public BorrowCallback(Context context, EmptyRequest request) {
        super(context, request);
    }

    @Override
    public void onResponse(Call<EmptyRequest> call, Response<EmptyRequest> response) {

        if (onResponseBase(response)) {
            Log.i(TAG, "onResponse: sending true");
            EventBus.getDefault().post(new BorrowMessage(true));
        } else {
            Log.i(TAG, "onResponse: sending false");
            EventBus.getDefault().post(new BorrowMessage(false));
        }
    }

    @Override
    public void onFailure(Call<EmptyRequest> call, Throwable t) {

        onFailureBase(t, getEventBusMessage());
    }

    private BorrowMessage getEventBusMessage() {
        return new BorrowMessage();
    }
}