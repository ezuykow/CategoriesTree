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

    /**
     * Сохраняет категорию в БД, если она не дублирующаяся, иначе выбрасывает исключение
     * @param category сохраняемая категория
     * @throws RepeatedCategoryException если категория уже существует
     * @author ezuykow
     */
    public void saveCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RepeatedCategoryException("Категория '" + category.getName() + "' уже существует!");
        }
        categoryRepository.save(category);
    }

    /**
     * Сохраняет категории в БД, если они не дублируются, иначе выбрасывает исключение
     * @param categories сохраняемые категории
     * @throws RepeatedCategoryException если хотя бы одна из категорий уже существует
     * @author ezuykow
     */
    public void saveAll(List<Category> categories) {
        if (!categoryRepository.findCategoriesByNameIn(categories.stream()
                .map(Category::getName)
                .toList()).isEmpty())
        {
            throw new RepeatedCategoryException("Одна или несколько категорий уже существуют!");
        }
        categoryRepository.saveAll(categories);
    }

    /**
     * Возвращает все категории из БД списком по id владельца
     * @param ownerId id владельца
     * @return список категорий из БД
     * @author ezuykow
     */
    public List<Category> findAllByOwnerId(long ownerId) {
        return categoryRepository.findCategoryByOwnerId(ownerId);
    }

    /**
     * Находит в БД категорию с именем {@code categoryName} или выбрасывает исключение
     * @param categoryName имя искомой категории
     * @return найденная категория
     * @throws CategoryNotFoundException если категории с таким именем не существует
     * @author ezuykow
     */
    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));
    }

    /**
     * Удаляет из БД категорию по имени и id владельца
     * @param categoryName имя удаляемой категории
     * @param ownerId id владельца
     * @author ezuykow
     */
    @Transactional
    public void removeByNameAndOwnerId(String categoryName, long ownerId) {
        categoryRepository.deleteCategoryByNameAndOwnerId(categoryName, ownerId);
    }

    //-----------------API END-------------------
}
