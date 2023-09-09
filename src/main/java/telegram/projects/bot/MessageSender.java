package telegram.projects.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Autowired
    TelegramBot telegramBot;

    public SendMessage sendMessageWithText(Update update, String text) {
        var message = new SendMessage(update.message().chat().id(), text);
        telegramBot.execute(message);
        return message;
    }

    public SendMessage sendMessageWithText(CallbackQuery callbackQuery, String text) {
        var message = new SendMessage(callbackQuery.message().chat().id(), text);
        telegramBot.execute(message);
        return message;
    }

    public SendMessage sendMessageWithKeyboard(CallbackQuery callbackQuery, String text, Keyboard keyboard) {
        var message = new SendMessage(callbackQuery.message().chat().id(), text).replyMarkup(keyboard);
        telegramBot.execute(message);
        return message;
    }
    public SendMessage sendMessageWithKeyboard(Update update, String text, Keyboard keyboard) {
        var message = new SendMessage(update.message().chat().id(), text).replyMarkup(keyboard);
        telegramBot.execute(message);
        return message;
    }

    public SendMessage sendMessageWithKeyboard(long chatId, String text, Keyboard keyboard) {
        var message = new SendMessage(chatId, text).replyMarkup(keyboard);
        telegramBot.execute(message);
        return message;
    }
    public SendMessage sendMessageWithText(long chatId, String text) {
        var message = new SendMessage(chatId, text);
        telegramBot.execute(message);
        return message;
    }
}
