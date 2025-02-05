package org.example.dresstoimpressservice.model;

import jakarta.persistence.*;

@Entity(name = "banner")
public class Banner {

    @Id
    @GeneratedValue
    private Long id;
    private String photo;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Show show;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
