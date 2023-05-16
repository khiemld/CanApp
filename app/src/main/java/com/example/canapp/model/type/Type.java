package com.example.canapp.model.type;

import com.example.canapp.model.task.Task;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Type implements Serializable {

    @SerializedName("_id")
    String _id;

    String name;

    int index;

    String plan;

    boolean active;

    List<Task> tasks;

    int __v;

    int id;

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type(int id, String name, int index) {
        this.id = id;
        this.name = name;
        this.index = index;
    }

    public Type() {
    }

    public Type(int id, String name, int index, String plan, boolean active, List<Task> tasks,
                int __v) {
        this.id = id;
        this.name = name;
        this.index = index;
        this.plan = plan;
        this.active = active;
        this.tasks = tasks;
        this.__v = __v;
    }

    public Type(String _id, String name, int index, String plan, boolean active, List<Task> tasks,
                int __v, int id) {
        this._id = _id;
        this.name = name;
        this.index = index;
        this.plan = plan;
        this.active = active;
        this.tasks = tasks;
        this.__v = __v;
        this.id = id;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
