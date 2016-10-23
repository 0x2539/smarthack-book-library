package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class RecommendedMessage extends BaseRestEventBusMessage {

    public RecommendedMessage() {
        super();
    }

    public RecommendedMessage(boolean success) {
        super(success);
    }

    public RecommendedMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }
}
