package com.example.canapp.model.user;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("image")
    private String avatar;

    @SerializedName("address")
    private String address;

    @SerializedName("major")
    private String major;

    @SerializedName("phone")
    private String phone;

    @SerializedName("active")
    private boolean active;

    @SerializedName("birthday")
    private String birthday;

    public User() {
    }

    public User(String _id) {
        this._id = _id;
    }

    public User(String _id, String name, String email, String password, String avatar, String address, String major, String phone, boolean active) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.major = major;
        this.phone = phone;
        this.active = active;
    }

    public User(String name, String email, String address, String major, String phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.major = major;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
