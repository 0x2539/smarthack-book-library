package com.example.alexbuicescu.smartlibraryandroid.rest.responses;

import com.example.alexbuicescu.smartlibraryandroid.pojos.Book;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class MainBooksResponse {

    @SerializedName("model")
    private String model;

    @SerializedName("pk")
    private long bookId;

    @SerializedName("fields")
    private Book book;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

