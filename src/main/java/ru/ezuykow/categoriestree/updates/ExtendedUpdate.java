package ru.ezuykow.categoriestree.updates;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import ru.ezuykow.categoriestree.commands.Command;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.exceptions.UnknownCommandException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ezuykow
 */
@RequiredArgsConstructor
public class ExtendedUpdate {

    private final Update update;

    //-----------------API START-----------------

    /**
     * Парсит апдейт с телеграма, заполняет и возвращает {@link ParsedCommand}
     * @return {@link ParsedCommand} разобранная команда
     * @author ezuykow
     */
    public ParsedCommand parseCommand() {
        try {
            Command cmd;
            List<String> args;
            String text;

            if (hasCaption()) {
                text = getCaption().trim().replaceAll("\\s+", " ");
            } else {
                text = getMessageText().trim().replaceAll("\\s+", " ");
            }

            if (text.contains(" ")) {
                cmd = Command.valueOf(text.substring(1, text.indexOf(" ")).toUpperCase());
                args = Arrays.asList(text.substring(text.indexOf(" ") + 1).split(" "));
            } else {
                cmd = Command.valueOf(text.substring(1).toUpperCase());
                args = Collections.emptyList();
            }
            return new ParsedCommand(getChatId(), cmd, args, getMessage().document());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException();
        }
    }

    /**
     * Проверяет есть ли в апдейте команда
     * @return true если команда есть, false в противном случае
     * @author ezuykow
     */
    public boolean isCommand() {
        return (hasMessageText() && getMessageText().matches("/.*")) ||
                (hasCaption() && getCaption().matches("/.*"));
    }

    /**
     * Проверяет есть ли в апдейте сообщение {@link Message}
     * @return true если сообщение есть, false в противном случае
     * @author ezuykow
     */
    public boolean hasMessage() {
        return getMessage() != null;
    }

    /**
     * Проверяет есть ли в апдейте подпись {@link Message#caption()}
     * @return true если подпись есть, false в противном случае
     * @author ezuykow
     */
    public boolean hasCaption() {
        return getCaption() != null;
    }

    /**
     * Проверяет есть ли в апдейте текст сообщения {@link Message#text()}
     * @return true если текст сообщения есть, false в противном случае
     * @author ezuykow
     */
    public boolean hasMessageText() {
        return hasMessage() && (getMessageText() != null);
    }

    /**
     * @return id чата, с которого пришло сообщение <br>
     * Используя {@link Update#message()} <br>
     * {@link Message#chat()} <br>
     * {@link Chat#id()}
     * @author ezuykow
     */
    public long getChatId() {
        return update.message().chat().id();
    }

    /**
     * Возвращает сообщение из апдейта {@link Update#message()}
     * @return сообщение {@link Message} из апдейта
     * @author ezuykow
     */
    public Message getMessage() {
        return update.message();
    }

    /**
     * Возвращает подпись из апдейта {@link Message#caption()}
     * @return подпись {@link Message#caption()} из апдейта
     * @author ezuykow
     */
    public String getCaption() {
        return update.message().caption();
    }

    /**
     * Возвращает подпись из апдейта {@link Message#text()}
     * @return подпись {@link Message#text()} из апдейта
     * @author ezuykow
     */
    public String getMessageText() {
        return getMessage().text();
    }

    //-----------------API END-------------------
}
