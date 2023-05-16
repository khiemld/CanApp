package com.example.canapp.model.task;

import com.example.canapp.model.user.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {

    @SerializedName("column")
    String column;

    int typeId;

    int id;

    String plan;

    int index;

    @SerializedName("title")
    String name;

    boolean active;

    List<User> members;

    int __v;

    String _id;

    public Task() {
    }

    public Task(int typeId, int id, String name) {
        this.typeId = typeId;
        this.id = id;
        this.name = name;
    }

    public Task(int typeId, int id, int index, String name) {
        this.typeId = typeId;
        this.id = id;
        this.index = index;
        this.name = name;
    }

    public Task(int typeId, int id, String plan, int index, String name, boolean active,
                List<User> members, int __v) {
        this.typeId = typeId;
        this.id = id;
        this.plan = plan;
        this.index = index;
        this.name = name;
        this.active = active;
        this.members = members;
        this.__v = __v;
    }

    public Task(int typeId, int id, String plan, int index, String name, boolean active,
                List<User> members, int __v, String _id) {
        this.typeId = typeId;
        this.id = id;
        this.plan = plan;
        this.index = index;
        this.name = name;
        this.active = active;
        this.members = members;
        this.__v = __v;
        this._id = _id;
    }

    public Task(String column, int typeId, int id, String plan, int index, String name,
                boolean active,
                List<User> members, int __v, String _id) {
        this.column = column;
        this.typeId = typeId;
        this.id = id;
        this.plan = plan;
        this.index = index;
        this.name = name;
        this.active = active;
        this.members = members;
        this.__v = __v;
        this._id = _id;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
