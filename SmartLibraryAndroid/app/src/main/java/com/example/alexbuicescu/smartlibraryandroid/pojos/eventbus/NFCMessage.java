package com.example.alexbuicescu.smartlibraryandroid.pojos.eventbus;

/**
 *
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class NFCMessage extends BaseRestEventBusMessage {

    private String nfcValue;

    public NFCMessage() {
        super();
    }

    public NFCMessage(String nfcValue) {
        super(true);
        this.nfcValue = nfcValue;
    }

    public NFCMessage(boolean success) {
        super(success);
    }

    public NFCMessage(boolean success, String errorMessage) {
        super(success, errorMessage);
    }

    public String getNfcValue() {
        return nfcValue;
    }

    public void setNfcValue(String nfcValue) {
        this.nfcValue = nfcValue;
    }
}
