package ru.ezuykow.categoriestree.commands.executors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.commands.CommandExecutor;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.excel.ExcelCategoriesTreeBuilder;
import ru.ezuykow.categoriestree.messages.MessageSender;

import java.io.File;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class DownloadCommandExec implements CommandExecutor {

    private final ExcelCategoriesTreeBuilder excelCategoriesTreeBuilder;
    private final MessageSender messageSender;

    //-----------------API START-----------------

    /**
     * Принимает {@link ParsedCommand} и исполняет команду /download
     * @param parsedCommand - команда с аргументами
     */
    @Override
    public void execute(ParsedCommand parsedCommand) {
        File file = excelCategoriesTreeBuilder.build();

        if (file == null) {
            messageSender.send(parsedCommand.chatId(), "В дереве нет ни одной категории");
            return;
        }

        messageSender.sendFile(parsedCommand.chatId(), file);
        file.delete();
    }

    //-----------------API END-------------------
}
