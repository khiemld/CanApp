package com.example.canapp.model.rate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageRate implements Serializable {
    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("rate")
    private Rate rate;

    public MessageRate() {
    }

    public MessageRate(String error, String message, Rate rate) {
        this.error = error;
        this.message = message;
        this.rate = rate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
