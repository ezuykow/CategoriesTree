package ru.ezuykow.categoriestree.excel;

import com.pengrad.telegrambot.model.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.exceptions.DocumentUploadException;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author ezuykow
 */
@Component
public class DocumentUploader {

    private static final String FILE_API_URL_PREFIX = "https://api.telegram.org/file/bot";
    private static final String BOT_API_URL_PREFIX = "https://api.telegram.org/bot";
    private static final String BOT_API_METHOD_GET_FILE = "/getFile?file_id=";

    @Value("${bot.token}")
    private String botToken;

    //-----------------API START-----------------

    public File upload(Document doc) {
        try {
            return downloadFile(fileUrl(doc), newTempFile());
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

    private URL fileUrl(Document doc) throws Exception {
        return new URL(FILE_API_URL_PREFIX + botToken + "/" + getFilePath(doc));
    }

    private String getFilePath(Document doc) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(fileInfoUrl(doc).openStream()))) {
            return new JSONObject(in.readLine()).getJSONObject("result").getString("file_path");
        }
    }

    private URL fileInfoUrl(Document doc) throws IOException {
        return new URL(BOT_API_URL_PREFIX + botToken + BOT_API_METHOD_GET_FILE + doc.fileId());
    }

    private File newTempFile() throws IOException {
        return File.createTempFile("tempCategories", ".xlsx");
    }
}
