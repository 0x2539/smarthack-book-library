package com.example.alexbuicescu.smartlibraryandroid.rest.callbacks;

import android.content.Context;
import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.BaseRestEventBusMessage;
import com.example.alexbuicescu.smartlibraryandroid.pojos.MyError;
import com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus.RestCallbackMessage;
import com.example.alexbuicescu.smartlibraryandroid.rest.RestClient;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public abstract class HttpCallbackCustom<RequestT, ResponseT> implements Callback<ResponseT> {

    private static final String TAG = HttpCallbackCustom.class.getSimpleName();
    private Context context;
    private RequestT request;

    public HttpCallbackCustom() {

    }

    public HttpCallbackCustom(Context context) {
        this.context = context;
    }

    public HttpCallbackCustom(Context context, RequestT request) {
        this.context = context;
        this.request = request;
    }

    /**
     * Returns true if request was successful
     *
     * @param response
     * @param eventBusMessage
     * @return
     */
    protected boolean onResponseBase(Response<ResponseT> response, BaseRestEventBusMessage... eventBusMessage) {
        try {
            getError(response, eventBusMessage);

            // send success message
            sendEvent(true, null, eventBusMessage);
            Log.i(TAG, response.body().getClass().getSimpleName() + " got body: " + new Gson().toJson(response.body()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    protected void onFailureBase(Throwable t, BaseRestEventBusMessage... eventBusMessage) {

        Log.e("BACKEND_FAILURE", t.getLocalizedMessage() + " \n " + t.getMessage());
        t.printStackTrace();

        sendEvent(false, "UNKNOWN", eventBusMessage);
    }

    protected void getError(Response<?> response, BaseRestEventBusMessage... eventBusMessage) throws Exception {
        try {
            if (response != null && !response.isSuccessful() && response.errorBody() != null) {
//                Converter<ResponseBody, MyError> errorConverter = retrofit.responseBodyConverter(MyError.class, new Annotation[0]);
                Converter<ResponseBody, MyError> errorConverter = RestClient.retrofit.responseBodyConverter(MyError.class, new Annotation[0]);
//                Converter<ResponseBody, MyError> errorConverter = (Converter<ResponseBody, MyError>) GsonConverterFactory.create().responseBodyConverter(MyError.class, new Annotation[0], null);

                MyError error = errorConverter.convert(response.errorBody());

                Log.e("BACKEND_ERROR", this.getClass().getName() + ": " + error.getError());
                sendEvent(false, error.getError(), eventBusMessage);
                throw new Exception(error.getError());
            }

            if (response != null && !response.isSuccessful()) {
                Log.e("BACKEND_ERROR", this.getClass().getName() + ";");
                sendEvent(false, "REQUEST_FAILED", eventBusMessage);
                throw new Exception("REQUEST_FAILED");
            }
        } catch (IOException e) {

            e.printStackTrace();
            sendEvent(false, "UNKNOWN", eventBusMessage);
            throw new Exception("UNKNOWN");
        }
    }

    private void sendEvent(boolean success, String message, BaseRestEventBusMessage... eventBusMessage) {
        if (eventBusMessage != null && eventBusMessage.length > 0) {

            eventBusMessage[0].setSuccess(success);
            eventBusMessage[0].setErrorMessage(message);

            sendEvent(eventBusMessage[0]);
        }

        sendEvent(new RestCallbackMessage(success, message));
    }

    private void sendEvent(BaseRestEventBusMessage eventBusMessage) {
        EventBus.getDefault().post(eventBusMessage);
    }

    public Context getContext() {
        return context;
    }

    public RequestT getRequest() {
        return request;
    }
}
