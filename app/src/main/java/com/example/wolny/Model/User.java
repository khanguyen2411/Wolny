package com.example.wolny.Model;

import java.util.Objects;

public class User {
    String userID;
    String username;
    String profileImage;
    String summary;
    String skills;
    String country;

    public User(String userID, String username, String profileImage, String summary, String skills, String country) {
        this.userID = userID;
        this.username = username;
        this.profileImage = profileImage;
        this.summary = summary;
        this.skills = skills;
        this.country = country;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", summary='" + summary + '\'' +
                ", skills='" + skills + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUserID(), user.getUserID()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getProfileImage(), user.getProfileImage()) &&
                Objects.equals(getSummary(), user.getSummary()) &&
                Objects.equals(getSkills(), user.getSkills()) &&
                Objects.equals(getCountry(), user.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getUsername(), getProfileImage(), getSummary(), getSkills(), getCountry());
    }
}
