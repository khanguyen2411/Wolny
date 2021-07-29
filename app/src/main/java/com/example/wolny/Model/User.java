package com.example.wolny.Model;

public class User {
    String username;
    String profileImage;

    public User(String username, String profileImage) {
        this.username = username;
        this.profileImage = profileImage;
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
}
