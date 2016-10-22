package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class BorrowMessage extends BaseRestEventBusMessage {

    public BorrowMessage() {
        super();
    }

    public BorrowMessage(boolean success) {
        super(success);
    }

    public BorrowMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
