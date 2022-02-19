package com.example.wolny.Model;

import java.io.Serializable;
import java.util.Objects;

public class Bid implements Serializable {
    String jobId;
    String employerID;
    String freelancerID;
    String freelancerName;
    String freelancerAvatar;
    String description;
    String time;
    String budget;

    public Bid(){

    }

    public Bid(String jobId, String employerID, String freelancerID, String freelancerName, String freelancerAvatar, String description, String time, String budget) {
        this.jobId = jobId;
        this.employerID = employerID;
        this.freelancerID = freelancerID;
        this.freelancerName = freelancerName;
        this.freelancerAvatar = freelancerAvatar;
        this.description = description;
        this.time = time;
        this.budget = budget;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid)) return false;
        Bid bid = (Bid) o;
        return Objects.equals(getJobId(), bid.getJobId()) &&
                Objects.equals(getFreelancerID(), bid.getFreelancerID()) &&
                Objects.equals(getFreelancerName(), bid.getFreelancerName()) &&
                Objects.equals(getFreelancerAvatar(), bid.getFreelancerAvatar()) &&
                Objects.equals(getDescription(), bid.getDescription()) &&
                Objects.equals(getTime(), bid.getTime()) &&
                Objects.equals(getBudget(), bid.getBudget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJobId(), getFreelancerID(), getFreelancerName(), getFreelancerAvatar(), getDescription(), getTime(), getBudget());
    }
}
