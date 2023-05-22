package com.example.canapp.model.discussion;

public class Like {
    String user;
    String _id;

    public Like(String user, String _id) {
        this.user = user;
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
