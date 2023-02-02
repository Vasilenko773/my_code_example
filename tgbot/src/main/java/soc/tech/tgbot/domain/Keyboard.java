package soc.tech.tgbot.domain;

import liquibase.pro.packaged.S;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Keyboard {
    InlineKeyboard inlineKeyboard;

    public SendMessage setButtons(SendMessage sendMessage, String type) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();

        switch (type){
            case "start":
                keyboardRow.add(new KeyboardButton("Старт"));
                keyboard.add(keyboardRow);
                break;
            case "main":
                KeyboardRow keyboardFirstRow = new KeyboardRow();
                keyboardFirstRow.add(new KeyboardButton("Добавить"));
                keyboardFirstRow.add(new KeyboardButton("Удалить"));

                KeyboardRow keyboardSecondRow = new KeyboardRow();
                keyboardSecondRow.add(new KeyboardButton("Список отслеживаемых"));
                keyboardSecondRow.add(new KeyboardButton("Получить текущие"));

                KeyboardRow keyboardThirdRow = new KeyboardRow();
                keyboardThirdRow.add(new KeyboardButton("Помощь"));
                keyboardThirdRow.add(new KeyboardButton("Стоп"));

                keyboard.add(keyboardFirstRow);
                keyboard.add(keyboardSecondRow);
                keyboard.add(keyboardThirdRow);
                break;
            case "add":
            case "del":
                keyboardRow.add(new KeyboardButton("Отмена"));

                keyboard.add(keyboardRow);
                break;
            case "confirm":
                sendMessage.setReplyMarkup(inlineKeyboard.confirm());
                return sendMessage;
            case "help":
                sendMessage.setReplyMarkup(inlineKeyboard.questions());
                return sendMessage;
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
