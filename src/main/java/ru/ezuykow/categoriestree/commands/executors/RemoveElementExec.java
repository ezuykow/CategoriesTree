package ru.ezuykow.categoriestree.commands.executors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.commands.CommandExecutor;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.messages.MessageSender;
import ru.ezuykow.categoriestree.services.CategoryService;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class RemoveElementExec implements CommandExecutor {

    private final CategoryService categoryService;
    private final MessageSender messageSender;

    //-----------------API START-----------------

    @Override
    public void execute(ParsedCommand parsedCommand) {
        categoryService.removeByName(parsedCommand.args().get(0));
        messageSender.send(parsedCommand.chatId(), "Категория удалена");
    }

    //-----------------API END-------------------
}
