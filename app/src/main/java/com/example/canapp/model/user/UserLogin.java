package com.example.canapp.model.user;

import com.example.canapp.model.user.User;
import com.google.gson.annotations.SerializedName;

public class UserLogin{
    private boolean error;

    @SerializedName("message")
    private String message;

    private User user;

    public UserLogin() {
    }

    public UserLogin(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public UserLogin(boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
