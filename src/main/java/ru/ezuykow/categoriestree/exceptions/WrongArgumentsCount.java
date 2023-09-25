package ru.ezuykow.categoriestree.exceptions;

/**
 * @author ezuykow
 */
public class WrongArgumentsCount extends RuntimeException{

    public WrongArgumentsCount() {
        super("Неверное количество аргументов!");
    }
}
