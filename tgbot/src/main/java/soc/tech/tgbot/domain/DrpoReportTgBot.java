package soc.tech.tgbot.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;
import soc.tech.tgbot.domain.chat.Chat;
import soc.tech.tgbot.domain.chat.ChatTgService;
import soc.tech.tgbot.domain.employee.Employee;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class DrpoReportTgBot extends TelegramLongPollingBot {

    private final Keyboard keyboard;
    private final DrpoReportRgBotConfig drpoReportRgBotConfig;
    private final ChatTgService chatTgService;
    private final DateFormatBuilder dateFormatBuilder;
    private final BotCommands botCommands;

    @Override
    public String getBotUsername() {
        return drpoReportRgBotConfig.getTgName();
    }

    @Override
    public String getBotToken() {
        return drpoReportRgBotConfig.getTgToken();
    }

    @PostConstruct
    public void botInit() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            response(update.getMessage().getChatId(), botCommands.getResponse(update));
        } else if (update.hasCallbackQuery()) {
            callbackResponse(update);
        }

    }

    @Scheduled(cron = "${tg.bot.cron.yesterday}")
    public void sendMsgYesterday() {
        LocalDate now = LocalDate.now();
        LocalDate date;
        if (now.getDayOfWeek().toString().equals("MONDAY")){
            date = LocalDate.now().minusDays(3);
        }else{
            date = LocalDate.now().minusDays(1);
        }

        sendReport(date, date);
    }

    @Scheduled(cron = "${tg.bot.cron.today}")
    public void sendMsgToday() {
        LocalDate date = LocalDate.now();
        sendReport(date, date);
    }

    private void sendReport(LocalDate from, LocalDate to) {
        List<Chat> chats = chatTgService.chatsAndEmployeeHoursFromByTo(from, to);
        for (Chat chat : chats) {
            for (Employee employee : chat.getEmployees()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.enableMarkdown(true);
                sendMessage.setChatId(chat.getId());
                String yesterday = dateFormatBuilder.dateByFormat(new ReportDate(from, "dd.MM.yyyy"));
                BigDecimal hours = new BigDecimal(employee.getHours()).setScale(2, RoundingMode.UP);
                sendMessage.setText(employee.getName().concat(", отчет за ").concat(yesterday).concat(". Итого часов: " + hours));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void response(Long chatId, List<Response> responses) {
        for (Response response : responses){
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(chatId);
            sendMessage.setText(response.getMessage());
            try {
                keyboard.setButtons(sendMessage, response.getKeyboardType());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void callbackResponse(Update update){
        DeleteMessage deleteMessage = new DeleteMessage(update.getCallbackQuery().getMessage().getChatId().toString(), update.getCallbackQuery().getMessage().getMessageId());
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        response(update.getCallbackQuery().getFrom().getId(), botCommands.getResponse(update));
    }
}