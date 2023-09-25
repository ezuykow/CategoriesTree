package ru.ezuykow.categoriestree.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ezuykow.categoriestree.commands.executors.*;
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
    private final DownloadCommandExec downloadCommandExec;
    private final UploadCommandExec uploadCommandExec;

    //-----------------API START-----------------

    /**
     * Принимает апдейт, парсит команду и аргументы и вызывает необходимый экзекутор
     * @param update апдейт {@link ExtendedUpdate} с командой
     * @author ezuykow
     */
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
            case DOWNLOAD -> downloadCommandExec.execute(parsedCommand);
            case UPLOAD -> uploadCommandExec.execute(parsedCommand);
        }
    }
}
