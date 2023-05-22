package com.example.canapp.model.discussion;

import java.util.List;

public class CommentResponse {
    String error;
    String message;
    List <CommentInDiscuss> comment;

    public CommentResponse(String error, String message, List<CommentInDiscuss> comment) {
        this.error = error;
        this.message = message;
        this.comment = comment;
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

    public List<CommentInDiscuss> getComment() {
        return comment;
    }

    public void setComment(List<CommentInDiscuss> comment) {
        this.comment = comment;
    }
}
