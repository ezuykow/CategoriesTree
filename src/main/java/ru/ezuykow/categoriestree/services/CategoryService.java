package ru.ezuykow.categoriestree.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ezuykow.categoriestree.entities.Category;
import ru.ezuykow.categoriestree.exceptions.CategoryNotFoundException;
import ru.ezuykow.categoriestree.exceptions.RepeatedCategoryException;
import ru.ezuykow.categoriestree.repositories.CategoryRepository;

import java.util.List;

/**
 * @author ezuykow
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //-----------------API START-----------------

    public void saveCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RepeatedCategoryException("Категория '" + category.getName() + "' уже существует!");
        }
        categoryRepository.save(category);
    }

    public void saveAll(List<Category> categories) {
        if (!categoryRepository.findCategoriesByNameIn(categories.stream()
                .map(Category::getName)
                .toList()).isEmpty())
        {
            throw new RepeatedCategoryException("Одна или несколько категорий уже существуют!");
        }
        categoryRepository.saveAll(categories);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));
    }

    @Transactional
    public void removeByName(String categoryName) {
        categoryRepository.deleteCategoryByName(categoryName);
    }

    //-----------------API END-------------------
}
