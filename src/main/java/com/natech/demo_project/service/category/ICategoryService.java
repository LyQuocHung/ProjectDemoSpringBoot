package com.natech.demo_project.service.category;

import com.natech.demo_project.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    void deleteCategory(Long id);
}
