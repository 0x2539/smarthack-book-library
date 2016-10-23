package com.example.alexbuicescu.smartlibraryandroid.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class Book {
    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("description")
    private String description;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("return_date")
    private String returnDate;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("genre")
    private BookGenreEnum genre;

    private boolean dueSoon;
    private boolean alreadyRead;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public BookGenreEnum getGenre() {
        return genre;
    }

    public void setGenre(BookGenreEnum genre) {
        this.genre = genre;
    }

    public boolean isDueSoon() {
        return dueSoon;
    }

    public void setDueSoon(boolean dueSoon) {
        this.dueSoon = dueSoon;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(boolean alreadyRead) {
        this.alreadyRead = alreadyRead;
    }
}
