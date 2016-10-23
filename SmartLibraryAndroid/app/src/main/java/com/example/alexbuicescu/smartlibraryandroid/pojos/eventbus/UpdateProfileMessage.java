package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class UpdateProfileMessage extends BaseRestEventBusMessage {

    public UpdateProfileMessage() {
        super();
    }

    public UpdateProfileMessage(boolean success) {
        super(success);
    }

    public UpdateProfileMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
