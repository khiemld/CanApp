package com.example.canapp.model.project;

public class Project {
    private String nameOwner;
    private String nameProject;
    private String description;
    private int avatarLeader;
    private String dateaddProject;

    public Project(String nameOwner, String nameProject, String description, int avatarLeader, String dateaddProject) {
        this.nameOwner = nameOwner;
        this.nameProject = nameProject;
        this.description = description;
        this.avatarLeader = avatarLeader;
        this.dateaddProject = dateaddProject;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvatarLeader() {
        return avatarLeader;
    }

    public void setAvatarLeader(int avatarLeader) {
        this.avatarLeader = avatarLeader;
    }

    public String getDateaddProject() {
        return dateaddProject;
    }

    public void setDateaddProject(String dateaddProject) {
        this.dateaddProject = dateaddProject;
    }
}
