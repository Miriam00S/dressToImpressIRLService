package org.example.dresstoimpressservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "styling")
public class Styling {

    @Id
    @GeneratedValue
    private Long id;


    private String name;

    private String photo;

    private String description;

    private Boolean isPrivate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Show show;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(
            mappedBy = "styling",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Vote> votes = new ArrayList<>();

    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setStyling(this);
    }

    public void removeVote(Vote vote) {
        votes.remove(vote);
        vote.setStyling(null);
    }

    public int getTotalVotes() {
        return votes.stream().mapToInt(Vote::getPoints).sum();
    }

    public int getPlace(List<Styling> allStylings) {
        allStylings.sort((s1, s2) -> Integer.compare(s2.getTotalVotes(), s1.getTotalVotes()));
        return allStylings.indexOf(this) + 1;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
