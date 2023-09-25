package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class RepeatedCategoryException extends RuntimeException {

    public RepeatedCategoryException(String categoryName) {
        super("Категория '" + categoryName + "' уже существует!");
    }
}
