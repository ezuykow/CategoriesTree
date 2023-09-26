package ru.ezuykow.categoriestree.commands.executors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.commands.CommandExecutor;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.excel.ExcelCategoriesTreeBuilder;
import ru.ezuykow.categoriestree.exceptions.DocumentBuildingException;
import ru.ezuykow.categoriestree.messages.MessageSender;

import java.io.File;
import java.io.IOException;

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
        File file = createTempFile();

        try {
            excelCategoriesTreeBuilder.build(parsedCommand.ownerId(), file);
            messageSender.sendFile(parsedCommand.chatId(), file);
        } catch (IOException e) {
            throw new DocumentBuildingException();
        } finally {
            file.delete();
        }
    }

    //-----------------API END-------------------

    private File createTempFile() {
        try {
            return File.createTempFile("categories", ".xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
