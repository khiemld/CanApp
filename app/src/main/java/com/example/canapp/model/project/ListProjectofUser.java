package com.example.canapp.model.project;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListProjectofUser implements Serializable {
    @SerializedName("error")
    boolean error;

    @SerializedName("message")
    String message;

    @SerializedName("plan")
    List<DetailProject> plan;

    public ListProjectofUser() {
    }

    public ListProjectofUser(boolean error, String message, List<DetailProject> plan) {
        this.error = error;
        this.message = message;
        this.plan = plan;
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

    public List<DetailProject> getPlan() {
        return plan;
    }

    public void setPlan(List<DetailProject> plan) {
        this.plan = plan;
    }
}
