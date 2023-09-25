package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String categoryName) {
        super("Категории '" + categoryName + "' не существует");
    }
}
