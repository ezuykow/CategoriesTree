package ru.ezuykow.categoriestree.commands.executors;

import com.pengrad.telegrambot.model.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.commands.CommandExecutor;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.entities.Category;
import ru.ezuykow.categoriestree.excel.DocumentUploader;
import ru.ezuykow.categoriestree.excel.ExcelCategoriesParser;
import ru.ezuykow.categoriestree.messages.MessageSender;
import ru.ezuykow.categoriestree.services.CategoryService;

import java.io.File;
import java.util.List;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class UploadCommandExec implements CommandExecutor {

    private static final String EXCEL_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private final DocumentUploader documentUploader;
    private final ExcelCategoriesParser excelCategoriesParser;
    private final CategoryService categoryService;
    private final MessageSender messageSender;

    //-----------------API START-----------------

    @Override
    public void execute(ParsedCommand parsedCommand) {
        if (parsedCommand.document() == null) {
            messageSender.send(parsedCommand.chatId(), "Эта команда отправляется с документом Excel");
            return;
        }

        if (documentIsExcelFile(parsedCommand.document())) {
            File file = documentUploader.upload(parsedCommand.document());
            List<Category> newCategories = excelCategoriesParser.parse(file);
            categoryService.saveAll(newCategories);
            messageSender.send(parsedCommand.chatId(), "Категории с файла добавлены");
        } else {
            messageSender.send(parsedCommand.chatId(), "Неизвестный формат документа (принимаются только *.xlsx)");
        }
    }

    //-----------------API END-------------------

    private boolean documentIsExcelFile(Document doc) {
        return doc.mimeType().equals(EXCEL_MIME_TYPE);
    }
}
