package com.example.wolny.Model;

public class Bid {
    String jobId;
    String freelancerID;
    String freelancerName;
    String freelancerAvatar;
    String description;
    String time;
    String budget;

    public Bid(){

    }

    public Bid(String jobId, String freelancerID, String freelancerName, String freelancerAvatar, String description, String time, String budget) {
        this.jobId = jobId;
        this.freelancerID = freelancerID;
        this.freelancerName = freelancerName;
        this.freelancerAvatar = freelancerAvatar;
        this.description = description;
        this.time = time;
        this.budget = budget;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getFreelancerID() {
        return freelancerID;
    }

    public void setFreelancerID(String freelancerID) {
        this.freelancerID = freelancerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public String getFreelancerAvatar() {
        return freelancerAvatar;
    }

    public void setFreelancerAvatar(String freelancerAvatar) {
        this.freelancerAvatar = freelancerAvatar;
    }
}
