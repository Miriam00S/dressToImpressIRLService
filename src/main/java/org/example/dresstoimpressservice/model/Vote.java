package org.example.dresstoimpressservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity(name = "vote")
public class Vote {


    @Id
    @GeneratedValue
    private Long id;

    private Integer points;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Styling styling;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Styling getStyling() {
        return styling;
    }

    public void setStyling(Styling styling) {
        this.styling = styling;
    }
}
