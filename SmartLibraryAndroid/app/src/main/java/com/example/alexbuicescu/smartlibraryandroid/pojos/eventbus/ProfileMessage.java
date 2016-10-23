package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class ProfileMessage extends BaseRestEventBusMessage {

    public ProfileMessage() {
        super();
    }

    public ProfileMessage(boolean success) {
        super(success);
    }

    public ProfileMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
