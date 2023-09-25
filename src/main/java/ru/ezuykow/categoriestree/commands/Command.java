package ru.ezuykow.categoriestree.commands;

/**
 * Перечисление доступных команд бота с описаниями
 * @author ezuykow
 */
public enum Command {
    HELP("/help", "Выводит список всех доступных команд и краткое их описание"),
    VIEWTREE("/viewTree", "Отображает дерево категорий в структурированном виде"),
    ADDELEMENT("/addElement <..> <..>", "С аргументом <Название категории> добавляет в дерево " +
            "новый корневой элемент, с аргументами <Название родительской категории>  <Название категории> добавляет " +
            "подкатегорию к указанной категории"),
    REMOVEELEMENT("/removeElement <..>", "С аргументом <Название категории> удаляет категорию " +
            "и ее подкатегории"),
    DOWNLOAD("/download", "Получить Excel документ с деревом категорий"),
    UPLOAD("/upload", "Отправить Excel документ с деревом категорий для сохранения");

    public final String displayedName;
    public final String description;

    Command(String displayedName, String description) {
        this.displayedName = displayedName;
        this.description = description;
    }
}
