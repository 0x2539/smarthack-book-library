package com.example.alexbuicescu.smartlibraryandroid.managers;

import com.example.alexbuicescu.smartlibraryandroid.pojos.Book;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 10/22/16.
 */
public class BooksManager {
    private static BooksManager ourInstance = new BooksManager();
    private ArrayList<MainBooksResponse> mainBooksResponses = new ArrayList<>();
    private ArrayList<MainBooksResponse> searchedResultBooks = new ArrayList<>();
    private ArrayList<MainBooksResponse> borrowedBooks = new ArrayList<>();

    public static BooksManager getInstance() {
        return ourInstance;
    }

    private BooksManager() {
    }

    public ArrayList<MainBooksResponse> getMainBooksResponses() {
        if (mainBooksResponses == null) {
            mainBooksResponses = new ArrayList<>();
        }
        return mainBooksResponses;
    }

    public void setMainBooksResponses(ArrayList<MainBooksResponse> mainBooksResponses) {
        this.mainBooksResponses = mainBooksResponses;
    }

    public ArrayList<MainBooksResponse> getSearchedResultBooks() {
        if (searchedResultBooks == null) {
            searchedResultBooks = new ArrayList<>();
        }
        return searchedResultBooks;
    }

    public void setSearchedResultBooks(ArrayList<MainBooksResponse> searchedResultBooks) {
        this.searchedResultBooks = searchedResultBooks;
    }

    public ArrayList<MainBooksResponse> getBorrowedBooks() {
        if (searchedResultBooks == null) {
            searchedResultBooks = new ArrayList<>();
        }
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<MainBooksResponse> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
