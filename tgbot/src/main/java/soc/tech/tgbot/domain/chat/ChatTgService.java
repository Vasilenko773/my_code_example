package soc.tech.tgbot.domain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.tgbot.domain.employee.Employee;
import soc.tech.tgbot.domain.employee.EmployeeTgService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatTgService {

    private final ChatTgRepository chatTgRepository;
    private final EmployeeTgService employeeTgService;

    public List<Chat> chatsAndEmployeeHoursFromByTo(LocalDate from, LocalDate to) {
        List<Chat> chats = chatTgRepository.chats();
        if (!chats.isEmpty()) {
            for (Chat chat : chats) {
                List<Employee> employees = employeeTgService.employeesAndHoursByChatId(chat.getId(), from, to);
                chat.getEmployees().addAll(employees);
            }
        }
        return chats;
    }

    public boolean save(Chat chat) {
        if (chatTgRepository.chatById(chat.getId()).getId() == null) {
            chatTgRepository.save(chat);
            return true;
        }
        return false;
    }

    public void removeEmployee(Integer employeeId, Long chatId) {
        chatTgRepository.deleteUser(employeeId, chatId);
    }

    public void removeChat(Long id) {
        chatTgRepository.deleteChat(id);
    }

}
