package org.example.dresstoimpressservice.dto;

import java.time.LocalDateTime;

public class CreateShowDto {
    private String topic;

    private Long creatorId;

    private Integer maxParticipantsNumber;

    private LocalDateTime joiningDate;

    private LocalDateTime votingTime;

    private String banner;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getMaxParticipantsNumber() {
        return maxParticipantsNumber;
    }

    public void setMaxParticipantsNumber(Integer maxParticipantsNumber) {
        this.maxParticipantsNumber = maxParticipantsNumber;
    }

    public LocalDateTime getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDateTime joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalDateTime getVotingTime() {
        return votingTime;
    }

    public void setVotingTime(LocalDateTime votingTime) {
        this.votingTime = votingTime;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
