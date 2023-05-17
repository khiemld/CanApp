package com.example.canapp.model.project;

import com.example.canapp.model.type.Type;
import com.example.canapp.model.user.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailProject implements Serializable {
    @SerializedName("_id")
    String _id;

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("manager")
    User manager;

    @SerializedName("beginTime")
    String beginTime;

    @SerializedName("endTime")
    String endTime;

    @SerializedName("active")
    boolean active;

    @SerializedName("members")
    List<User> members;

    @SerializedName("list")
    List<Type> list;

    public DetailProject() {
    }

    public DetailProject(String _id, String name, String description, User manager, String beginTime, String endTime, boolean active, List<User> members, List<Type> list) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.active = active;
        this.members = members;
        this.list = list;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public List<Type> getList() {
        return list;
    }

    public void setList(List<Type> list) {
        this.list = list;
    }
}
