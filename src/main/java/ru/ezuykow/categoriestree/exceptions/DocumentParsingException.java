package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class DocumentParsingException extends RuntimeException{

    public DocumentParsingException() {
        super("Ошибка обработки документа");
    }
}
