package ru.ezuykow.categoriestree.commands.executors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.commands.CommandExecutor;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.entities.Category;
import ru.ezuykow.categoriestree.exceptions.WrongArgumentsCount;
import ru.ezuykow.categoriestree.messages.MessageSender;
import ru.ezuykow.categoriestree.services.CategoryService;

import java.util.List;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class AddElementCommandExec implements CommandExecutor {

    private final CategoryService categoryService;
    private final MessageSender messageSender;

    //-----------------API START-----------------

    /**
     * Принимает {@link ParsedCommand} и исполняет команду /addElement
     * @param parsedCommand - команда с аргументами
     */
    @Override
    public void execute(ParsedCommand parsedCommand) {
        categoryService.saveCategory(createCategory(parsedCommand.args()));
        messageSender.send(parsedCommand.chatId(), "Категория добавлена");
    }

    //-----------------API END-------------------

    private Category createCategory(List<String> args) {
        Category category;
        switch (args.size()) {
            case 1 -> category = new Category(args.get(0));
            case 2 -> category = new Category(
                    categoryService.findCategoryByName(args.get(0)),
                    args.get(1));
            default -> throw  new WrongArgumentsCount();
        }

        return category;
    }
}
