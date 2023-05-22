package com.example.canapp.model.discussion;

import java.util.List;

public class DetailDiscussion {
    String _id;
    String user;
    String title;
    String text;
    String name;
    String avatar;
    String plan;
    String date;
    boolean block;
    boolean active;
    List<LikeInDiscuss> likes;
    List<CommentInDiscuss> comments;

    public DetailDiscussion() {

    }
    public DetailDiscussion(  String name, String avatar, String date,List<CommentInDiscuss> comments) {
        this.name = name;
        this.avatar = avatar;
        this.date = date;
        this.comments = comments;
    }



    public DetailDiscussion(String _id, String user, String title, String text, String name, String avatar, String plan, String date, boolean block, boolean active, List<LikeInDiscuss> likes, List<CommentInDiscuss> comments) {
        this._id = _id;
        this.user = user;
        this.title = title;
        this.text = text;
        this.name = name;
        this.avatar = avatar;
        this.plan = plan;
        this.date = date;
        this.block = block;
        this.active = active;
        this.likes = likes;
        this.comments = comments;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<LikeInDiscuss> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeInDiscuss> likes) {
        this.likes = likes;
    }

    public List<CommentInDiscuss> getComments() {
        return comments;
    }

    public void setComments(List<CommentInDiscuss> comments) {
        this.comments = comments;
    }
}
