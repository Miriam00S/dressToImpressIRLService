package org.example.dresstoimpressservice.dto;

public class CreateBannerDto {

    private String photo;

    private Long showId;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }
}
