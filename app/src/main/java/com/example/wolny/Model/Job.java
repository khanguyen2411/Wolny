package com.example.wolny.Model;

public class Job {
    String employerID;
    String freelancerID;
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

    public Job(String employerID, String freelancerID, String category, String title, String description, String type, String budget, String skillRequired, String status, String currency, String time) {
        this.employerID = employerID;
        this.freelancerID = freelancerID;
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

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
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

    public String getFreelancerID() {
        return freelancerID;
    }

    public void setFreelancerID(String freelancerID) {
        this.freelancerID = freelancerID;
    }
}
