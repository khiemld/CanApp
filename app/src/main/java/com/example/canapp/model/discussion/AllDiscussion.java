package com.example.canapp.model.discussion;

import com.example.canapp.Comment;

import java.util.List;

public class AllDiscussion {
    /* "error": false,
    "message": "Create Post successfully",
    "post":*/
    String error;
    String message;
    List<DetailDiscussion> post;

    public AllDiscussion() {
    }

    public AllDiscussion(String error, String message, List<DetailDiscussion> post) {
        this.error = error;
        this.message = message;
        this.post = post;
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

    public List<DetailDiscussion> getPost() {
        return post;
    }

    public void setPost(List<DetailDiscussion> post) {
        this.post = post;
    }
}
