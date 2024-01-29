package com.librasys.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Loan {
    @SerializedName("ID")
    private long id;

    @SerializedName("UserID")
    private long userId;

    @SerializedName("LoanDate")
    private String loanDate;

    @SerializedName("LoanDetail")
    private List<LoanDetail> loanDetail;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLoanDate() {
        return this.loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public List<LoanDetail> getLoanDetail() {
        return this.loanDetail;
    }

    public void setLoanDetail(List<LoanDetail> loanDetail) {
        this.loanDetail = loanDetail;
    }

}
