package com.example.alexbuicescu.smartlibraryandroid.managers;

import android.util.Log;

import com.example.alexbuicescu.smartlibraryandroid.pojos.Book;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;
import com.example.alexbuicescu.smartlibraryandroid.utils.Utils;

import java.util.ArrayList;

/**
 * Created by alexbuicescu on 10/22/16.
 */
public class BooksManager {
    private static final String TAG = BooksManager.class.getSimpleName();
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
        this.mainBooksResponses = updateDueSoonOthers(mainBooksResponses);
    }

    public ArrayList<MainBooksResponse> getSearchedResultBooks() {
        if (searchedResultBooks == null) {
            searchedResultBooks = new ArrayList<>();
        }
        return searchedResultBooks;
    }

    public void setSearchedResultBooks(ArrayList<MainBooksResponse> searchedResultBooks) {
        this.searchedResultBooks = updateDueSoonOthers(searchedResultBooks);
    }

    public ArrayList<MainBooksResponse> getBorrowedBooks() {
        if (borrowedBooks == null) {
            borrowedBooks = new ArrayList<>();
        }
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<MainBooksResponse> borrowedBooks) {
        this.borrowedBooks = updateDueSoon(borrowedBooks);
        this.mainBooksResponses = updateDueSoonOthers(this.mainBooksResponses);
        this.searchedResultBooks = updateDueSoonOthers(this.searchedResultBooks);
        this.recommendedBooks = updateDueSoonOthers(this.recommendedBooks);
    }

    public ArrayList<MainBooksResponse> getRecommendedBooks() {
        if (recommendedBooks == null) {
            recommendedBooks = new ArrayList<>();
        }
        return recommendedBooks;
    }

    public void setRecommendedBooks(ArrayList<MainBooksResponse> recommendedBooks) {
        this.recommendedBooks = updateDueSoonOthers(recommendedBooks);
    }

    private ArrayList<MainBooksResponse> updateDueSoon(ArrayList<MainBooksResponse> books) {
        if (books == null) {
            return null;
        }
        ArrayList<MainBooksResponse> newBooks = new ArrayList<>();
        for (MainBooksResponse book : books) {
            Log.i(TAG, "updateDueSoon: " + book.getBook().getTitle() + " " + Utils.isDateSoon(book.getBook().getReturnDate()));
            book.getBook().setDueSoon(Utils.isDateSoon(book.getBook().getReturnDate()));
            book.getBook().setAlreadyRead(Utils.isDateInPast(book.getBook().getReturnDate()));
            newBooks.add(book);
        }
        return newBooks;
    }

    private ArrayList<MainBooksResponse> updateDueSoonOthers(ArrayList<MainBooksResponse> books) {
        if (books == null) {
            return null;
        }
        ArrayList<MainBooksResponse> newBooks = new ArrayList<>();
        for (MainBooksResponse book : books) {
            boolean found = false;
            for (MainBooksResponse book2 : borrowedBooks) {
                if (book.getBookId() == book2.getBookId()) {
                    book.getBook().setDueSoon(Utils.isDateSoon(book2.getBook().getReturnDate()));
                    book.getBook().setAlreadyRead(Utils.isDateInPast(book2.getBook().getReturnDate()));
//                    Log.i(TAG, "updateDueSoonOthers: " + book2.getBook().getTitle() + " " + book2.getBook().getReturnDate() + " " + book.getBook().isDueSoon());
                    newBooks.add(book);
                    found = true;
                }
            }
            if (!found) {
                newBooks.add(book);
            }
        }
        return newBooks;
    }
}
