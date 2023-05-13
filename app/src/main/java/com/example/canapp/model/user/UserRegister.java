package com.example.canapp.model.user;

public class UserRegister {
    private boolean error;
    private String result;

    public UserRegister(boolean error, String result) {
        this.error = error;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
