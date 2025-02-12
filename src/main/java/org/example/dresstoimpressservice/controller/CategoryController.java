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
        // Checking if the given show exists
        Optional<Show> optionalShow = showRepository.findById(createCategoryDto.getShowId());

        if (optionalShow.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show with the given ID does not exist.");
        }

        Show show = optionalShow.get();

        // Creating a new category
        Category category = new Category();
        category.setName(createCategoryDto.getName());

        // Adding a category to the show
        show.addCategory(category);

        // Database entry
        categoryRepository.save(category);
        showRepository.save(show);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
}
