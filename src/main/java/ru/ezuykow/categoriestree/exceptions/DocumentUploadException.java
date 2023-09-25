package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class DocumentUploadException extends RuntimeException{

    public DocumentUploadException() {
        super("Ошибка загрузки документа");
    }
}
