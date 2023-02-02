package soc.tech.tgbot.domain.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeTgService {
    private final EmployeeTgRepository employeeTgRepository;
    private final DateFormatBuilder dateFormatBuilder;

    public List<Employee> employeesAndHoursByChatId(Long id, LocalDate from, LocalDate to) {
        Map<String, Employee> employeeMap = new HashMap<>();
        List<Employee> employees = employeeTgRepository.employeesByChat(id);
        if (!employees.isEmpty()) {
            for (Employee e : employees) {
                Double hours = employeeTgRepository.hoursFromToByUser(from, to, e.getId());
                if (employeeMap.containsKey(e.getName())) {
                    hours += employeeMap.get(e.getName()).getHours();
                }
                e.setHours(hours);
                employeeMap.put(e.getName(), e);
            }
        }
        return new ArrayList<>(employeeMap.values());
    }

    public List<Employee> actualEmployeesAndHours(Long id) {
        List<Employee> resultEmployee = new ArrayList<>();
        List<Employee> employeesHours = employeesAndHoursByChatId(id, LocalDate.now(), LocalDate.now());
        for (Employee employee : employeesHours) {
            BigDecimal hours = new BigDecimal(employee.getHours()).setScale(2, RoundingMode.UP);
            resultEmployee.add(new Employee(employee.getName().concat(", отчет за ").
                    concat(dateFormatBuilder.dateByFormat(new ReportDate(LocalDate.now(), "dd.MM.yyyy"))).concat(". Итого часов: " + hours)));
        }
        return resultEmployee;
    }


    public Integer save(Employee employee, Long chatId) {
        List<Employee> outsideTheChat = employeeTgRepository.employeeByUserDataByName(employee.getName());
        List<Employee> employeeByChat = employeesByChat(chatId);
        List<Employee> employeeByTelegram = employeeByName(employee.getName());
        Set<Integer> indexes = outsideTheChat.stream().map(Employee::getId).collect(Collectors.toSet());
        if (outsideTheChat.isEmpty()) {
            return 1;
        } else if (!isContained(employeeByTelegram, employee.getName()) && !isContained(employeesByChat(chatId), employee.getName())) {
            outsideTheChat.forEach(employeeTgRepository::save);
            indexes.forEach(i -> employeeTgRepository.saveChatsEmployees(chatId, i));
            return 0;
        } else if (!isContained(employeeByChat, employee.getName()) && isContained(employeeByTelegram, employee.getName())) {
            indexes.forEach(i -> employeeTgRepository.saveChatsEmployees(chatId, i));
            return 0;
        }
        return 2;
    }

    private boolean isContained(List<Employee> employees, String name) {
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Integer delete(Employee employee, Long chatId) {
        List<Employee> outsideTheChat = employeeTgRepository.employeeByUserDataByName(employee.getName());
        List<Employee> employees = employeeTgRepository.employeeByName(employee.getName());
        List<Employee> employeesByChat = employeeTgRepository.employeesByChat(chatId);
        Set<Boolean> isContained = employeesByChat.stream().map(i -> i.getName().equals(employee.getName())).collect(Collectors.toSet());
        if (!employees.isEmpty() && isContained.contains(true)) {
            employees.forEach(e -> employeeTgRepository.deleteEmployee(e.getId(), chatId));
            return 0;
        } else if (!outsideTheChat.isEmpty() && isContained.contains(false)) {
            return 2;
        }
        return 1;
    }


    public List<Employee> employeeByName(String name) {
        return employeeTgRepository.employeeByName(name);
    }

    public List<Employee> employeesByChat(Long id) {
        return employeeTgRepository.employeesByChat(id);
    }

    public List<Employee> employees() {
        return employeeTgRepository.employees();
    }

    public List<Employee> employeesByChatGroupByName(Long id) {
        return employeeTgRepository.employeesByChatGroupByName(id);
    }
}
