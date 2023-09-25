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

    public void performUpdate(ExtendedUpdate update) {
        if (update.isCommand()) {
            commandManager.performCommand(update);
        }
    }

    //-----------------API END-------------------

}
