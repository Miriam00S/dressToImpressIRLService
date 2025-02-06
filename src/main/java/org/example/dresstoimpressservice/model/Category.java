package org.example.dresstoimpressservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Show show;

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
}
