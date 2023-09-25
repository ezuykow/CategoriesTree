package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class RepeatedCategoryException extends RuntimeException {

    public RepeatedCategoryException(String msg) {
        super(msg);
    }
}
