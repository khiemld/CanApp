package com.example.canapp.model.user;

// Model này cho mấy cái item trong cái list trong cái plan
public class ItemInListPlan {
    String _id;

    public ItemInListPlan(String _id) {
        this._id = _id;
    }

    public ItemInListPlan() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
