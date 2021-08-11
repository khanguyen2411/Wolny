package com.example.wolny.Model;

public class User {
    String username;
    String profileImage;
    String summary;
    String skills;

    public User(String username, String profileImage, String summary, String skills) {
        this.username = username;
        this.profileImage = profileImage;
        this.summary = summary;
        this.skills = skills;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
