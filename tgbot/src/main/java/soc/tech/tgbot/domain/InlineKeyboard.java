package soc.tech.tgbot.domain;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboard {

    public InlineKeyboardMarkup confirm() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Да");
        button.setCallbackData("/yes");

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup questions() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> keyboardSecondRow = new ArrayList<>();
        List<InlineKeyboardButton> keyboardThirdRow = new ArrayList<>();

        InlineKeyboardButton schedule = new InlineKeyboardButton();
        schedule.setText("Расписание рассылки");
        schedule.setCallbackData("/schedule");
        keyboardFirstRow.add(schedule);

        InlineKeyboardButton info = new InlineKeyboardButton();
        info.setText("Работа бота");
        info.setCallbackData("/info");
        keyboardSecondRow.add(info);

        InlineKeyboardButton authors = new InlineKeyboardButton();
        authors.setText("Авторы");
        authors.setCallbackData("/authors");
        keyboardThirdRow.add(authors);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardFirstRow);
        rowList.add(keyboardSecondRow);
        rowList.add(keyboardThirdRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
