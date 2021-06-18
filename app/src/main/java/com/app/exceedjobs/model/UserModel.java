package com.app.exceedjobs.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("_id")
    String id;

    @SerializedName("username")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("address")
    String address;

    @SerializedName("phone")
    String phone;

    @SerializedName("createdAt")
    String createdAt;

    @SerializedName("paid_status")
    String paymentStatus;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


}
