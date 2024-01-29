package com.librasys.models;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("ID")
    private long id;

    @SerializedName("StatusName")
    private String statusName;

    public Status() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
