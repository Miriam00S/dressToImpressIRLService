package org.example.dresstoimpressservice.repository;

import org.example.dresstoimpressservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
