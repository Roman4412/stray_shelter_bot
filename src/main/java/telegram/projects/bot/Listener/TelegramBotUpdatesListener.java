package telegram.projects.bot.Listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import telegram.projects.bot.MessageSender;
import telegram.projects.bot.Controller.ShelterBotController;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final ShelterBotController shelterBotController;
    private final MessageSender sender;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      ShelterBotController shelterBotController1, MessageSender sender) {
        this.telegramBot = telegramBot;
        this.shelterBotController = shelterBotController1;
        this.sender = sender;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.callbackQuery() != null) {
                logger.info("Processing update: {}", update.callbackQuery().data());
                shelterBotController.callbackQueryHandler(update.callbackQuery());
            } else if (update.message().text() != null && update.message().text().startsWith("/")) {
                logger.info("Processing update: {}", update.message().text());
                shelterBotController.commandHandler(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
