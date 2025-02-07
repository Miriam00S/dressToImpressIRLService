package org.example.dresstoimpressservice.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "show")
public class Show {

    @Id
    @GeneratedValue
    private Long id;
    private String topic;

    private Long creatorId;

    private Integer maxParticipantsNumber;

    private LocalDateTime joiningDate;

    private LocalDateTime votingTime;

    private String banner;

    @OneToMany(
            mappedBy = "show",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Styling> stylings = new ArrayList<>();

    @OneToMany(
            mappedBy = "show",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Category> categories = new ArrayList<>();


    @OneToMany(
            mappedBy = "show",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public void addStyling(Styling styling) {
        stylings.add(styling);
        styling.setShow(this);
    }

    public void removeStyling(Styling styling) {
        stylings.remove(styling);
        styling.setShow(null);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setShow(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setShow(null);
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.setShow(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.setShow(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Styling> getStylings() {
        return stylings;
    }

    public void setStylings(List<Styling> stylings) {
        this.stylings = stylings;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
