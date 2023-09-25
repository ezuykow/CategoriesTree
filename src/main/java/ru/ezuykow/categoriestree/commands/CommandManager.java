package ru.ezuykow.categoriestree.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ezuykow.categoriestree.commands.executors.AddElementCommandExec;
import ru.ezuykow.categoriestree.commands.executors.HelpCommandExec;
import ru.ezuykow.categoriestree.commands.executors.RemoveElementExec;
import ru.ezuykow.categoriestree.commands.executors.ViewTreeCommandExec;
import ru.ezuykow.categoriestree.updates.ExtendedUpdate;

/**
 * @author ezuykow
 */
@Service
@RequiredArgsConstructor
public class CommandManager {

    private final HelpCommandExec helpCommandExec;
    private final AddElementCommandExec addElementCommandExec;
    private final ViewTreeCommandExec viewTreeCommandExec;
    private final RemoveElementExec removeElementExec;

    //-----------------API START-----------------

    public void performCommand(ExtendedUpdate update) {
        ParsedCommand parsedCommand = update.parseCommand();
        executeCommand(parsedCommand);
    }

    //-----------------API END-------------------

    private void executeCommand(ParsedCommand parsedCommand) {
        switch (parsedCommand.command()) {
            case HELP -> helpCommandExec.execute(parsedCommand);
            case ADDELEMENT -> addElementCommandExec.execute(parsedCommand);
            case VIEWTREE -> viewTreeCommandExec.execute(parsedCommand);
            case REMOVEELEMENT -> removeElementExec.execute(parsedCommand);
        }
    }
}
