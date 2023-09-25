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

    public void send(long chatId, String msg) {
        telegramBot.execute(new SendMessage(chatId, msg));
    }

    public void sendFile(long chatId, File file) {
        telegramBot.execute(new SendDocument(chatId, file));
    }

    //-----------------API END-------------------
}
