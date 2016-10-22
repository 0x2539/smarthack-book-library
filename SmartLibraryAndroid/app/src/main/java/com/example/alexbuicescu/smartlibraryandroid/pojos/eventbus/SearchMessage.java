package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class SearchMessage extends BaseRestEventBusMessage {

    public SearchMessage() {
        super();
    }

    public SearchMessage(boolean success) {
        super(success);
    }

    public SearchMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
