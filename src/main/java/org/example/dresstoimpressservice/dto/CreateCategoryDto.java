package org.example.dresstoimpressservice.dto;

public class CreateCategoryDto {

    private String name;
    private Long showId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }
}
