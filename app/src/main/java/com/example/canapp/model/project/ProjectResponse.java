package com.example.canapp.model.project;

public class ProjectResponse {

    String error;
    String message;
    ProjectFull plan;

    public ProjectResponse(String error, String message, ProjectFull plan) {
        this.error = error;
        this.message = message;
        this.plan = plan;
    }

    public ProjectResponse() {
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

    public ProjectFull getPlan() {
        return plan;
    }

    public void setPlan(ProjectFull plan) {
        this.plan = plan;
    }
}
