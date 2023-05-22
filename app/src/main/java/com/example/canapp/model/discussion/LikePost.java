package com.example.canapp.model.discussion;

import java.util.List;

public class LikePost {
    public String error;
    public String message;
    List<Like> likes;

    public LikePost(String error, String message, List<Like> likes) {
        this.error = error;
        this.message = message;
        this.likes = likes;
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

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}
