package com.example.canapp.model.rate;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rate implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("member")
    private String idMember;

    @SerializedName("judge")
    private String idJudge;

    @SerializedName("plan")
    private String idPlan;

    @SerializedName("attitude")
    private int attitude;

    @SerializedName("expertise")
    private int expertise;

    @SerializedName("discipline")
    private int discipline;

    @SerializedName("collaborate")
    private int collaborate;

    @SerializedName("performance")
    private int performance;

    @SerializedName("comment")
    private String comment;

    @SerializedName("createTime")
    private String createTime;

    @SerializedName("active")
    private boolean active;

    public Rate() {
    }

    public Rate(String id, String idMember, String idJudge, String idPlan, int attitude, int expertise, int discipline, int collaborate, int performance, String comment, String createTime, boolean active) {
        this.id = id;
        this.idMember = idMember;
        this.idJudge = idJudge;
        this.idPlan = idPlan;
        this.attitude = attitude;
        this.expertise = expertise;
        this.discipline = discipline;
        this.collaborate = collaborate;
        this.performance = performance;
        this.comment = comment;
        this.createTime = createTime;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMember() {
        return idMember;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public String getIdJudge() {
        return idJudge;
    }

    public void setIdJudge(String idJudge) {
        this.idJudge = idJudge;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public int getAttitude() {
        return attitude;
    }

    public void setAttitude(int attitude) {
        this.attitude = attitude;
    }

    public int getExpertise() {
        return expertise;
    }

    public void setExpertise(int expertise) {
        this.expertise = expertise;
    }

    public int getDiscipline() {
        return discipline;
    }

    public void setDiscipline(int discipline) {
        this.discipline = discipline;
    }

    public int getCollaborate() {
        return collaborate;
    }

    public void setCollaborate(int collaborate) {
        this.collaborate = collaborate;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
