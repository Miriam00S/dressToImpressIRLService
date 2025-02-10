package org.example.dresstoimpressservice.repository;

import org.example.dresstoimpressservice.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("SELECT s FROM show s JOIN s.categories c WHERE c.name = :categoryName")
    List<Show> findByCategoryName(@Param("categoryName") String categoryName);

    List<Show> findByTopicIgnoreCase(String topic);

    List<Show> findByCreatorId(Long creatorId);
}
