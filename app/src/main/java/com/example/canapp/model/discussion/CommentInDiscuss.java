package com.example.canapp.model.discussion;

import java.io.Serializable;

public class CommentInDiscuss implements Serializable {
    String user;
    String name;
    String avatar;
    String text;
    String date;
    String _id;

    public CommentInDiscuss() {
    }

    public CommentInDiscuss(String user, String name, String avatar, String text, String date, String _id) {
        this.user = user;
        this.name = name;
        this.avatar = avatar;
        this.text = text;
        this.date = date;
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
