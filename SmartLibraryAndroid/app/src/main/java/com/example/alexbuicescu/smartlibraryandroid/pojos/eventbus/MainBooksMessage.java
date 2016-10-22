package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class MainBooksMessage extends BaseRestEventBusMessage {

    public MainBooksMessage() {
        super();
    }

    public MainBooksMessage(boolean success) {
        super(success);
    }

    public MainBooksMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
