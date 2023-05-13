package com.example.canapp.model.project;

import com.example.canapp.model.user.ItemInListPlan;
import com.example.canapp.model.user.User;

import java.util.List;

public class ProjectFull {
    String name;
    String description;
    String manager;
    String beginTime;
    String endTime;
    boolean active;
    String _id;
    List<ItemInListPlan> members;

    /*Lười tạo model List quá hihi*/
    List<ItemInListPlan> list;

    int __v;

    public ProjectFull(String name, String description, String manager, String beginTime,
                       String endTime, boolean active, String _id, List<ItemInListPlan> members,
                       List<ItemInListPlan> list, int __v) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.active = active;
        this._id = _id;
        this.members = members;
        this.list = list;
        this.__v = __v;
    }

    public ProjectFull() {
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<ItemInListPlan> getMembers() {
        return members;
    }

    public void setMembers(List<ItemInListPlan> members) {
        this.members = members;
    }

    public List<ItemInListPlan> getList() {
        return list;
    }

    public void setList(List<ItemInListPlan> list) {
        this.list = list;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
