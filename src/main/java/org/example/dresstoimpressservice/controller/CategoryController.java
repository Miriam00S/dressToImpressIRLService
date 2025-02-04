package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.model.Category;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
