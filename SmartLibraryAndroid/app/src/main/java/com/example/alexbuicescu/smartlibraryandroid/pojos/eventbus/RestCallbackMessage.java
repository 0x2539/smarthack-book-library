package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class RestCallbackMessage extends BaseRestEventBusMessage {

    public RestCallbackMessage(boolean success) {
        super(success);
    }

    public RestCallbackMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
