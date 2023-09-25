package ru.ezuykow.categoriestree.updates;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ezuykow.categoriestree.commands.CommandManager;

/**
 * @author ezuykow
 */
@Service
@RequiredArgsConstructor
public class UpdateManager {

    private final CommandManager commandManager;

    //-----------------API START-----------------

    /**
     * "Выполняет" апдейт - если в нем есть команда, то передает его в {@link CommandManager}
     * @param update апдейт с телеграма
     * @author ezuykow
     */
    public void performUpdate(ExtendedUpdate update) {
        if (update.isCommand()) {
            commandManager.performCommand(update);
        }
    }

    //-----------------API END-------------------

}
