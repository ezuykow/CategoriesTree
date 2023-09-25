package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class UnknownCommandException extends RuntimeException{

    public UnknownCommandException() {
        super("Неизвестная команда!");
    }
}
