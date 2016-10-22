package com.example.alexbuicescu.smartlibraryandroid.managers;

import com.example.alexbuicescu.smartlibraryandroid.pojos.Book;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;
import com.example.alexbuicescu.smartlibraryandroid.utils.Utils;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 10/22/16.
 */
public class BooksManager {
    private static BooksManager ourInstance = new BooksManager();
    private ArrayList<MainBooksResponse> mainBooksResponses = new ArrayList<>();
    private ArrayList<MainBooksResponse> searchedResultBooks = new ArrayList<>();
    private ArrayList<MainBooksResponse> borrowedBooks = new ArrayList<>();
    private ArrayList<MainBooksResponse> recommendedBooks = new ArrayList<>();

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
        this.searchedResultBooks = updateDueSoon(searchedResultBooks);
    }

    public ArrayList<MainBooksResponse> getBorrowedBooks() {
        if (searchedResultBooks == null) {
            searchedResultBooks = new ArrayList<>();
        }
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<MainBooksResponse> borrowedBooks) {
        this.borrowedBooks = updateDueSoon(borrowedBooks);
    }

    public ArrayList<MainBooksResponse> getRecommendedBooks() {
        if (searchedResultBooks == null) {
            searchedResultBooks = new ArrayList<>();
        }
        return recommendedBooks;
    }

    public void setRecommendedBooks(ArrayList<MainBooksResponse> recommendedBooks) {
        this.recommendedBooks = updateDueSoon(recommendedBooks);
    }

    private ArrayList<MainBooksResponse> updateDueSoon(ArrayList<MainBooksResponse> books) {
        if (books == null) {
            return null;
        }
        ArrayList<MainBooksResponse> newBooks = new ArrayList<>();
        for (MainBooksResponse book : books) {
            book.getBook().setDueSoon(Utils.isDateSoon(book.getBook().getReturnDate()));
            newBooks.add(book);
        }
        return newBooks;
    }
}
