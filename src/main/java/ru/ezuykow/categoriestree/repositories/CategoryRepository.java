package ru.ezuykow.categoriestree.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ezuykow.categoriestree.entities.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author ezuykow
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findCategoryByName(String name);

    List<Category> findCategoryByOwnerId(long ownerId);

    boolean existsByName(String name);

    List<Category> findCategoriesByNameIn(List<String> names);

    void deleteCategoryByNameAndOwnerId(String name, long ownerId);
}
