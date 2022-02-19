package com.example.wolny.Model;

import java.util.Objects;

public class LastMessage {
    String type;
    String sender;
    String receiver;
    String content;
    String username;
    String avatarUrl;
    String freelancerID;

    public LastMessage() {
    }

    public LastMessage(String type, String sender, String receiver, String content, String username, String avatarUrl, String freelancerID) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.freelancerID = freelancerID;
    }

    public String getFreelancerID() {
        return freelancerID;
    }

    public void setFreelancerID(String freelancerID) {
        this.freelancerID = freelancerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LastMessage)) return false;
        LastMessage that = (LastMessage) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getSender(), that.getSender()) &&
                Objects.equals(getReceiver(), that.getReceiver()) &&
                Objects.equals(getContent(), that.getContent()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getAvatarUrl(), that.getAvatarUrl()) &&
                Objects.equals(getFreelancerID(), that.getFreelancerID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getSender(), getReceiver(), getContent(), getUsername(), getAvatarUrl(), getFreelancerID());
    }
}
