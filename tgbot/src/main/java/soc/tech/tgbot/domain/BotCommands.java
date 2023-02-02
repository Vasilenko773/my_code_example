package soc.tech.tgbot.domain;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import soc.tech.tgbot.domain.chat.Chat;
import soc.tech.tgbot.domain.chat.ChatTgService;
import soc.tech.tgbot.domain.employee.Employee;
import soc.tech.tgbot.domain.employee.EmployeeTgService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BotCommands {
    private final ChatTgService chatTgService;
    private final EmployeeTgService employeeTgService;
    private final HashMap<Long, String> nameBufferWhenAdding = new HashMap<>();
    private final HashMap<Long, String> nameBufferWhenDeleting = new HashMap<>();

    public List<Response> getResponse(Update update) {
        List<Response> responses = new ArrayList<>();
        if (update.hasMessage()) {
            Long id = update.getMessage().getChatId();
            String message = update.getMessage().getText();

            switch (message) {
                case "/start":
                case "Старт":
                    responses.add(start(id));
                    break;
                case "Добавить":
                    nameBufferWhenAdding.put(id, null);
                    responses.add(new Response("Напишите [Имя Фамилию] пользователя, которого хотите отслеживать", "add"));
                    break;
                case "Стоп":
                    chatTgService.removeChat(id);
                    responses.add(new Response("Вы прекратили работу с ботом", "start"));
                    break;
                case "Удалить":
                    nameBufferWhenDeleting.put(id, null);
                    responses.add(new Response("Напишите [Имя Фамилию] пользователя, которого хотите перестать отслеживать", "del"));
                    break;
                case "Список отслеживаемых":
                    responses.add(userList(id));
                    break;
                case "Помощь":
                    responses.add(new Response("Какой вопрос Вас интересует?", "help"));
                    break;
                case "Получить текущие":
                    for (Employee value : employeeTgService.actualEmployeesAndHours(update.getMessage().getChatId())) {
                        responses.add(new Response(value.getName(), "main"));
                    }
                    break;
                case "Отмена":
                    nameBufferWhenAdding.remove(id);
                    nameBufferWhenDeleting.remove(id);
                    responses.add(new Response("Вы успешно отменили добавление/удаление", "main"));
                    break;
                default:
                    if (nameBufferWhenAdding.containsKey(id)) {
                        responses.add(addUser(message, id));
                    } else if (nameBufferWhenDeleting.containsKey(id)) {
                        responses.add(delete(message, id));
                    } else {
                        responses.add(new Response("Нераспознанное действие", "start"));
                    }
                    break;
            }
        }
        actButton(update, responses);
        return responses;
    }

    private void actButton(Update update, List<Response> responses) {
        if (update.hasCallbackQuery()) {
            Long id = update.getCallbackQuery().getFrom().getId();
            String callback = update.getCallbackQuery().getData();

            switch (callback) {
                case "/yes":
                    if (nameBufferWhenAdding.containsKey(id)) {
                        responses.add(addUser(nameBufferWhenAdding.get(id), id));
                    }
                    if (nameBufferWhenDeleting.containsKey(id)) {
                        responses.add(delete(nameBufferWhenDeleting.get(id), id));
                    }
                    break;
                case "/schedule":
                    responses.add(new Response("Данный бот присылает трудозатраты за прошедший рабочий день в 9:00 и 18:00 ", "main"));
                    break;
                case "/authors":
                    responses.add(new Response("http://10.100.10.128/users/32\nhttp://10.100.10.128/users/93", "main"));
                    break;
                case "/info":
                    responses.add(new Response("Если вы не видите клавиатуру с кнопками, нажмите на первую кнопку справа на поле ввода.\nБот обновляет трудозатраты каждые 3 минуты.", "main"));
                    break;
            }
        }
    }

    private Response start(Long id) {
        Response response = new Response();
        if (!chatTgService.save(new Chat(id, new ArrayList<>()))) {
            response.setMessage("Ваша сессия с телеграмм ботом активна");
        } else {
            response.setMessage("Вы начали работу с ботом");
        }
        response.setKeyboardType("main");
        return response;
    }

    private Response addUser(String message, Long id) {
        switch (employeeTgService.save(new Employee(message), id)) {
            case 1:
                nameBufferWhenAdding.put(id, findSimilar(message));
                return new Response("Возможно, вы имели ввиду пользователя: " + findSimilar(message) + "? Если нет, проверьте введенные данные и попробуйте еще раз", "confirm");
            case 0:
                nameBufferWhenAdding.remove(id);
                return new Response("Теперь вы отслеживаете пользователя: " + message, "main");
            case 2:
                return new Response("Вы уже отслеживаете данного пользователя:", "add");
        }
        return new Response("Извините, что-то пошло не так", "main");
    }

    private Response userList(Long id) {
        StringBuilder text = new StringBuilder();
        for (Employee employee : employeeTgService.employeesByChatGroupByName(id)) {
            text.append(employee.getName()).append(", ");
        }
        String message = text.toString().isBlank() ? "Вы не добавили сотрудников для отслеживания" : text.substring(0, text.length() - 2);
        return new Response(message, "main");
    }

    private Response delete(String message, Long id) {
        switch (employeeTgService.delete(new Employee(message), id)) {
            case 0:
                nameBufferWhenDeleting.remove(id);
                return new Response("Вы больше не отслеживаете пользователя: " + message, "main");
            case 1:
                nameBufferWhenDeleting.put(id, findSimilar(message));
                return new Response("Возможно, вы имели ввиду пользователя: " + findSimilar(message) + "? Если нет, проверьте введенные данные и попробуйте еще раз", "confirm");
            case 2:
                return new Response("Вы и так не отслеживали данного пользователя", "del");
        }
        return new Response("Извините, что-то пошло не так", "main");
    }

    public String findSimilar(String name) {
        String ans = "";
        Double max = 0.0;
        for (Employee employee : employeeTgService.employees()) {
            double maxLength = Double.max(name.length(), employee.getName().length());
            if (max < (maxLength - StringUtils.getLevenshteinDistance(name.toLowerCase(), employee.getName().toLowerCase())) / maxLength) {
                max = (maxLength - StringUtils.getLevenshteinDistance(name.toLowerCase(), employee.getName().toLowerCase())) / maxLength;
                ans = employee.getName();
            }
        }
        return ans;
    }
}
