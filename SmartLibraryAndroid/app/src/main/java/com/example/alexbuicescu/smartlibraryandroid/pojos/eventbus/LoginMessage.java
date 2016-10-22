package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class LoginMessage extends BaseRestEventBusMessage {

    public LoginMessage() {
        super();
    }

    public LoginMessage(boolean success) {
        super(success);
    }

    public LoginMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
