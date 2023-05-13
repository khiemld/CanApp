package com.example.canapp.model.project;

import com.example.canapp.model.type.Type;
import com.example.canapp.model.user.User;

import java.util.List;

public class ProjectInProjectDetail {
    String _id;
    String name;
    String description;
    String beginTime;
    String endTime;
    boolean active;

    List<User> manager;
    List<User> members;
    List<Type> columns;

    public ProjectInProjectDetail() {
    }

    public ProjectInProjectDetail(String _id, String name, String description, String beginTime,
                                  String endTime, boolean active, List<User> manager,
                                  List<User> members, List<Type> columns) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.active = active;
        this.manager = manager;
        this.members = members;
        this.columns = columns;
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

    public List<User> getManager() {
        return manager;
    }

    public void setManager(List<User> manager) {
        this.manager = manager;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Type> getColumns() {
        return columns;
    }

    public void setColumns(List<Type> columns) {
        this.columns = columns;
    }
}
