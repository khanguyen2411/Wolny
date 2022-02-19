package com.example.wolny.Model;

import java.io.Serializable;

public class Job implements Serializable {
    String jobID;
    String employerID;
    String employerName;
    String freelancerID;
    String freelancerName;
    String country;
    String category;
    String title;
    String description;
    String type;
    String budget;
    String skillRequired;
    String status;
    String currency;
    String time;

    public Job() {
    }

    public Job(String jobID, String employerID, String employerName,
               String freelancerID, String freelancerName,
               String country, String category, String title,
               String description, String type, String budget,
               String skillRequired, String status, String currency, String time) {

        this.jobID = jobID;
        this.employerID = employerID;
        this.employerName = employerName;
        this.freelancerID = freelancerID;
        this.freelancerName = freelancerName;
        this.country = country;
        this.category = category;
        this.title = title;
        this.description = description;
        this.type = type;
        this.budget = budget;
        this.skillRequired = skillRequired;
        this.status = status;
        this.currency = currency;
        this.time = time;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getFreelancerName() {
        return freelancerName;
    }

    public void setFreelancerName(String freelancerName) {
        this.freelancerName = freelancerName;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public String getFreelancerID() {
        return freelancerID;
    }

    public void setFreelancerID(String freelancerID) {
        this.freelancerID = freelancerID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getSkillRequired() {
        return skillRequired;
    }

    public void setSkillRequired(String skillRequired) {
        this.skillRequired = skillRequired;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
