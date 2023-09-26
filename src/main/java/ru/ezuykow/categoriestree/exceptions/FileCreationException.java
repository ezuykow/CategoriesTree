package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class FileCreationException extends RuntimeException{

    public FileCreationException() {
        super("Ошибка создания временного файла");
    }
}
