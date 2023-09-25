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
            throw new RepeatedCategoryException(category.getName());
        }
        categoryRepository.save(category);
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
