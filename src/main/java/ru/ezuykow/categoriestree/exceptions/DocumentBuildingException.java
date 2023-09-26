package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class DocumentBuildingException extends RuntimeException{

    public DocumentBuildingException() {
        super("Ошибка создания документа Excel");
    }
}
