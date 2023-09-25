package ru.ezuykow.categoriestree.updates;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ezuykow.categoriestree.messages.MessageSender;

import java.util.List;

/**
 * @author ezuykow
 */
@Service
@RequiredArgsConstructor
public class UpdatesListenerImpl implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final UpdateManager updateManager;
    private final MessageSender messageSender;

    @PostConstruct
    private void init() {
        telegramBot.setUpdatesListener(this);
    }

    //-----------------API START-----------------

    @Override
    public int process(List<Update> updates) {
        for (Update tgUpdate : updates) {
            ExtendedUpdate update = new ExtendedUpdate(tgUpdate);
            try {
                updateManager.performUpdate(update);
            } catch (Exception e) {
                messageSender.send(update.getChatId(), e.getMessage());
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //-----------------API END-------------------
}
