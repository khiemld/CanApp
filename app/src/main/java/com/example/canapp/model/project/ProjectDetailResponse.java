package com.example.canapp.model.project;

import java.io.Serializable;

public class ProjectDetailResponse implements Serializable {

    boolean error;
    String message;
    ProjectInProjectDetail plan;

    public ProjectDetailResponse() {
    }

    public ProjectDetailResponse(boolean error, String message, ProjectInProjectDetail plan) {
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

    public ProjectInProjectDetail getPlan() {
        return plan;
    }

    public void setPlan(ProjectInProjectDetail plan) {
        this.plan = plan;
    }
}
