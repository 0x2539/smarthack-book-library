package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class LoanDateMessage extends BaseRestEventBusMessage {

    private String date;

    public LoanDateMessage() {
        super();
    }

    public LoanDateMessage(String date) {
        super(true);
        this.date = date;
    }

    public LoanDateMessage(boolean success) {
        super(success);
    }

    public LoanDateMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
