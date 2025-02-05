package org.example.dresstoimpressservice.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "show_category",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToOne(mappedBy = "show", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Banner banner;

    public void addStyling(Styling styling) {
        stylings.add(styling);
        styling.setShow(this);
    }

    public void removeStyling(Styling styling) {
        stylings.remove(styling);
        styling.setShow(null);
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getShows().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getShows().remove(this);
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        if (banner == null) {
            if (this.banner != null) {
                this.banner.setShow(null);
            }
        }
        else {
            banner.setShow(this);
        }
        this.banner = banner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Show)) return false;
        return id != null && id.equals(((Show) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
