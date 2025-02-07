package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreateCategoryDto;
import org.example.dresstoimpressservice.model.Category;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.repository.CategoryRepository;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ShowRepository showRepository;

    public CategoryController(CategoryRepository categoryRepository, ShowRepository showRepository) {
        this.categoryRepository = categoryRepository;
        this.showRepository = showRepository;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        // Sprawdzenie, czy podane show istnieje
        Optional<Show> optionalShow = showRepository.findById(createCategoryDto.getShowId());

        if (optionalShow.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show o podanym ID nie istnieje.");
        }

        Show show = optionalShow.get();

        // Tworzenie nowej kategorii
        Category category = new Category();
        category.setName(createCategoryDto.getName());

        // Dodanie kategorii do show
        show.addCategory(category);

        // Zapis do bazy
        categoryRepository.save(category);
        showRepository.save(show);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
}
