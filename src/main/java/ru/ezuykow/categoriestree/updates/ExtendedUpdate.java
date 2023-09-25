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

    public boolean isCommand() {
        return (hasMessageText() && getMessageText().matches("/.*")) ||
                (hasCaption() && getCaption().matches("/.*"));
    }

    public boolean hasMessage() {
        return getMessage() != null;
    }

    public boolean hasCaption() {
        return getCaption() != null;
    }

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

    public Message getMessage() {
        return update.message();
    }

    public String getCaption() {
        return update.message().caption();
    }

    public String getMessageText() {
        return getMessage().text();
    }

    //-----------------API END-------------------
}
