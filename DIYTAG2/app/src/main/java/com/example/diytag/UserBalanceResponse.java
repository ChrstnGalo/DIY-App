package com.example.diytag;

import com.google.gson.annotations.SerializedName;

public class UserBalanceResponse {
    @SerializedName("user_id")
    private int user_id;

    @SerializedName("balance")
    private double balance;

    // Constructor
    public UserBalanceResponse(int user_id, double balance) {
        this.user_id = user_id;
        this.balance = balance;
    }

    // Getters and Setters (kung kailangan)
    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

