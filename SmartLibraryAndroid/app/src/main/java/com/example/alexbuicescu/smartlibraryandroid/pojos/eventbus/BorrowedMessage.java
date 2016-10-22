package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class BorrowedMessage extends BaseRestEventBusMessage {

    public BorrowedMessage() {
        super();
    }

    public BorrowedMessage(boolean success) {
        super(success);
    }

    public BorrowedMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
