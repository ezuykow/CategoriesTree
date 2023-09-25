package ru.ezuykow.categoriestree.commands.executors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.commands.Command;
import ru.ezuykow.categoriestree.commands.CommandExecutor;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.messages.MessageSender;

import java.util.Arrays;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class HelpCommandExec implements CommandExecutor {

    private final MessageSender messageSender;

    //-----------------API START-----------------

    /**
     * Принимает {@link ParsedCommand} и исполняет команду /help
     * @param parsedCommand - команда с аргументами
     */
    @Override
    public void execute(ParsedCommand parsedCommand) {
        String msg = Arrays.stream(Command.values())
                .map(c -> c.displayedName + " - " + c.description + "\n")
                .reduce((c1, c2) -> c1 + c2).orElse("Error...");
        messageSender.send(parsedCommand.chatId(), msg);
    }

    //-----------------API END-------------------
}
