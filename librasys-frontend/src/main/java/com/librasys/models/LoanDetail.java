package com.librasys.models;

import com.google.gson.annotations.SerializedName;

public class LoanDetail {
    @SerializedName("ID")
    private long id;

    @SerializedName("ReturnDate")
    private String returnDate;

    @SerializedName("Fine")
    private long fine;

    @SerializedName("Book")
    private Book book;

    @SerializedName("BookID")
    private long bookId;

    @SerializedName("Status")
    private Status status;

    @SerializedName("StatusID")
    private long statusId;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public long getFine() {
        return this.fine;
    }

    public void setFine(long fine) {
        this.fine = fine;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getBookId() {
        return this.bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getStatusId() {
        return this.statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
}
