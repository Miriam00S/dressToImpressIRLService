package org.example.dresstoimpressservice.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "show")
public class Show {

    @Id
    @GeneratedValue
    private Long id;
    private String topic;

    private Long creatorId;

    @OneToMany(
            mappedBy = "show",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Styling> stylings = new ArrayList<>();

    public void addStyling(Styling styling) {
        stylings.add(styling);
        styling.setShow(this);
    }

    public void removeStyling(Styling styling) {
        stylings.remove(styling);
        styling.setShow(null);
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
}
