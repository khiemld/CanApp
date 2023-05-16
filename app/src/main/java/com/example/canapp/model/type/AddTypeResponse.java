package com.example.canapp.model.type;

public class AddTypeResponse {

    boolean error;
    String message;
    Type list;

    public AddTypeResponse() {
    }

    public AddTypeResponse(boolean error, String message, Type list) {
        this.error = error;
        this.message = message;
        this.list = list;
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

    public Type getList() {
        return list;
    }

    public void setList(Type list) {
        this.list = list;
    }
}
