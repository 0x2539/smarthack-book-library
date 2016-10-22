package com.example.alexbuicescu.smartlibraryandroid.rest.callbacks;

import android.content.Context;
import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.managers.BooksManager;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.LoanedTogetherWithMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class LoanedTogetherWithCallback extends HttpCallbackCustom<EmptyRequest, ArrayList<MainBooksResponse>> {

    private final String TAG = LoanedTogetherWithCallback.class.getSimpleName();

    public LoanedTogetherWithCallback(Context context, EmptyRequest request) {
        super(context, request);
    }

    @Override
    public void onResponse(Call<ArrayList<MainBooksResponse>> call, Response<ArrayList<MainBooksResponse>> response) {

        if (onResponseBase(response)) {
            Log.i(TAG, "onResponse: sending true");
            BooksManager.getInstance().setRecommendedBooks(response.body());
            EventBus.getDefault().post(new LoanedTogetherWithMessage(true));
        } else {
            Log.i(TAG, "onResponse: sending false");
            EventBus.getDefault().post(new LoanedTogetherWithMessage(false));
        }
    }

    @Override
    public void onFailure(Call<ArrayList<MainBooksResponse>> call, Throwable t) {

        onFailureBase(t, getEventBusMessage());
    }

    private LoanedTogetherWithMessage getEventBusMessage() {
        return new LoanedTogetherWithMessage();
    }
}