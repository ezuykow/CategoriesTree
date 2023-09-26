package ru.ezuykow.categoriestree.excel;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.request.GetFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.exceptions.DocumentUploadException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class DocumentUploader {

    private final TelegramBot bot;

    //-----------------API START-----------------

    /**
     * Принимает документ {@link Document} из апдейта, сохраняет его во временный файл и возвращает
     * @param doc {@link Document} из апдейта
     * @return загруженный файл {@link File}
     * @author ezuykow
     */
    public File upload(Document doc) {
        try {
            com.pengrad.telegrambot.model.File file = bot.execute(new GetFile(doc.fileId())).file();
            return downloadFile(new URL(bot.getFullFilePath(file)), newTempFile());
        } catch (Exception e) {
            throw new DocumentUploadException();
        }
    }

    //-----------------API END-------------------

    private File downloadFile(URL fileURL, File tempFile) throws IOException {
        try (ReadableByteChannel rbc = Channels.newChannel(fileURL.openStream());
             FileOutputStream fos = new FileOutputStream(tempFile))
        {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return tempFile;
        }
    }

    private File newTempFile() throws IOException {
        return File.createTempFile("tempCategories", ".xlsx");
    }
}
