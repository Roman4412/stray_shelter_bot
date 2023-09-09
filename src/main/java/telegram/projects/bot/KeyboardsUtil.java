package telegram.projects.bot;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;

@Component
public class KeyboardsUtil {
    public InlineKeyboardMarkup getMainKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Приют для кошек").callbackData("cats"),
                new InlineKeyboardButton("Приют для Собак").callbackData("dogs")
        );

        return keyboardMarkup;
    }
    public InlineKeyboardMarkup getShelterKeyboard() {
        //TODO должен быть один метод на 2 приюта с разной информацией. Реализовать через переопределение
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Информация о приюте").callbackData("info"),
                new InlineKeyboardButton("Взять питомца").callbackData("take_pet"))
                .addRow(new InlineKeyboardButton("Отправить отчет").callbackData("report"),
                        new InlineKeyboardButton("Помощь волонтера").callbackData("volunteer"))
                .addRow(new InlineKeyboardButton("Назад").callbackData("cancel"));
        return keyboardMarkup;
    }

    public InlineKeyboardMarkup getCancelButton() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Назад").callbackData("cancel")
        );
        return keyboardMarkup;
    }
}
