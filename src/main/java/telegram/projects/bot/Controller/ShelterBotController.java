package telegram.projects.bot.Controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import telegram.projects.bot.KeyboardsUtil;
import telegram.projects.bot.MessageSender;
import telegram.projects.bot.model.ChatState;

import static telegram.projects.bot.model.ChatState.*;

@Service
public class ShelterBotController {
    private Integer currentMessageId = 0;
    private ChatState chatState = NEW;
    private final Logger logger = LoggerFactory.getLogger(ShelterBotController.class);
    private final KeyboardsUtil keyboardsUtil;
    private final MessageSender sender;
    private final TelegramBot telegramBot;

    public ShelterBotController(KeyboardsUtil keyboardsUtil, MessageSender sender, TelegramBot telegramBot) {
        this.keyboardsUtil = keyboardsUtil;
        this.sender = sender;
        this.telegramBot = telegramBot;
    }

    public void callbackQueryHandler(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.data();
        long chatId = callbackQuery.message().chat().id();
        DeleteMessage deleteMessage = new DeleteMessage(chatId, currentMessageId);

        switch (callbackData) {
            case "cats":
                telegramBot.execute(deleteMessage);
                currentMessageId = telegramBot.execute(new SendMessage(chatId, "Меню приюта кошек")
                                .replyMarkup(keyboardsUtil.getShelterKeyboard()))
                        .message().messageId();
                chatState = SHELTER_MENU;
                break;
            case "dogs":
                telegramBot.execute(deleteMessage);
                currentMessageId = telegramBot.execute(new SendMessage(chatId, "Меню приюта собак")
                                .replyMarkup(keyboardsUtil.getShelterKeyboard()))
                        .message().messageId();
                chatState = SHELTER_MENU;
                break;
            case "info":
                telegramBot.execute(deleteMessage);
                currentMessageId = telegramBot.execute(new SendMessage(chatId, "Здесь будет информация о приюте")
                                .replyMarkup(keyboardsUtil.getCancelButton()))
                        .message().messageId();
                chatState = INFO_MENU;
                break;

            case "take_pet":
                telegramBot.execute(deleteMessage);
                currentMessageId = telegramBot.execute(new SendMessage(chatId, "Место для информации о том, как забрать питомца домой")
                                .replyMarkup(keyboardsUtil.getCancelButton()))
                        .message().messageId();
                chatState = TAKE_PET;
                break;

            case "report":
                telegramBot.execute(deleteMessage);
                currentMessageId = telegramBot.execute(new SendMessage(chatId, "Здесь можно отправить отчет")
                                .replyMarkup(keyboardsUtil.getCancelButton()))
                        .message().messageId();
                chatState = REPORT;
                break;

            case "volunteer":
                telegramBot.execute(deleteMessage);
                currentMessageId = telegramBot.execute(new SendMessage(chatId, "Здесь будет возможно связаться с волонтером")
                                .replyMarkup(keyboardsUtil.getCancelButton()))
                        .message().messageId();
                chatState = VOLUNTEER;
                break;

            case "cancel":
                logger.info("cancel is received");
                switch (chatState) {
                    case INFO_MENU:
                    case TAKE_PET:
                    case REPORT:
                    case VOLUNTEER:
                        EditMessageReplyMarkup editedKeyboard = new EditMessageReplyMarkup(chatId, currentMessageId).replyMarkup(keyboardsUtil.getShelterKeyboard());
                        EditMessageText editedText = new EditMessageText(chatId, currentMessageId, "Меню приюта кошек");
                        telegramBot.execute(editedText);
                        telegramBot.execute(editedKeyboard);
                        chatState = SHELTER_MENU;
                        break;
                    case SHELTER_MENU:
                        EditMessageReplyMarkup editedKeyboard1 = new EditMessageReplyMarkup(chatId, currentMessageId).replyMarkup(keyboardsUtil.getMainKeyboard());
                        EditMessageText editedText1 = new EditMessageText(chatId, currentMessageId, "Приветствуем в Stray Shelter");
                        telegramBot.execute(editedText1);
                        telegramBot.execute(editedKeyboard1);
                        chatState = MAIN;
                        break;
                }
                break;
            default:
                sender.sendMessageWithText(chatId, "необработано");
        }
        logger.info("callbackQueryHandler end");
    }

    public void commandHandler(Update update) {
        currentMessageId = telegramBot.execute(new SendMessage(update.message().chat().id(), "Приветствуем в Stray Shelter")
                .replyMarkup(keyboardsUtil.getMainKeyboard())).message().messageId();
    }

}
