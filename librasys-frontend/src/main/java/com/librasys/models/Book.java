package com.librasys.models;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("ID")
    private long id;

    @SerializedName("ISBN")
    private String ISBN;

    @SerializedName("Title")
    private String title;

    @SerializedName("Author")
    private String author;

    @SerializedName("Publisher")
    private String publisher;

    public Book() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getISBN() {
        return this.ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
