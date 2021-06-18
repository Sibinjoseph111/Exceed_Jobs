package com.app.exceedjobs.model;

import com.google.gson.annotations.SerializedName;

public class MessageModel {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("message")
    String message;

    @SerializedName("date")
    String date;

    @SerializedName("usertype")
    String userType;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getUserType() {
        return userType;
    }
}
