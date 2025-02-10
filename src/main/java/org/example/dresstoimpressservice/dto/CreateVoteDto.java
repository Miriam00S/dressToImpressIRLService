package org.example.dresstoimpressservice.dto;

public class CreateVoteDto {

    private Integer points;
    private Long stylingId;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Long getStylingId() {
        return stylingId;
    }

    public void setStylingId(Long stylingId) {
        this.stylingId = stylingId;
    }
}
