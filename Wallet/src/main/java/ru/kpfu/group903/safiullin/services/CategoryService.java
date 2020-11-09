package ru.kpfu.group903.safiullin.services;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;
import ru.kpfu.group903.safiullin.models.Category;
import ru.kpfu.group903.safiullin.repositories.CategoryRepository;

import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
