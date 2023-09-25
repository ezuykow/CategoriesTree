package ru.ezuykow.categoriestree.messages;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author ezuykow
 */
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final TelegramBot telegramBot;

    //-----------------API START-----------------

    /**
     * Отправляет сообщение {@code msg} в чат с id {@code chatId}
     * @param chatId id чата
     * @param msg сообщение
     * @author ezuykow
     */
    public void send(long chatId, String msg) {
        telegramBot.execute(new SendMessage(chatId, msg));
    }

    /**
     * Отправляет файл {@code file} в чат с id {@code chatId}
     * @param chatId id чата
     * @param file файл
     * @author ezuykow
     */
    public void sendFile(long chatId, File file) {
        telegramBot.execute(new SendDocument(chatId, file));
    }

    //-----------------API END-------------------
}
