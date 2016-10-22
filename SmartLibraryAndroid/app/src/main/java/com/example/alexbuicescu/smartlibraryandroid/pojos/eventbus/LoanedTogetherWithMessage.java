package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class LoanedTogetherWithMessage extends BaseRestEventBusMessage {

    public LoanedTogetherWithMessage() {
        super();
    }

    public LoanedTogetherWithMessage(boolean success) {
        super(success);
    }

    public LoanedTogetherWithMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
