package com.example.canapp.model.task;

public class AddTaskResponse {
    boolean error;
    String message;
    Task task;

    public AddTaskResponse() {
    }

    public AddTaskResponse(boolean error, String message, Task task) {
        this.error = error;
        this.message = message;
        this.task = task;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
