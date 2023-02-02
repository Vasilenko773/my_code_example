package soc.tech.tgbot.domain.employee;

import liquibase.pro.packaged.E;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeTgRepository {

    private final JdbcTemplate jdbc;

    public List<Employee> employeeByUserDataByName(String name) {
        return jdbc.query("select id, system_id, name from users where name = ?", (rs, row) -> {
            return new Employee(rs.getInt("id"), rs.getInt("system_id"), rs.getString("name"));
        }, name);
    }

    public void save(Employee employee) {
        jdbc.update("insert into employee_tg(id, system_id, name) values (?, ?, ?)",
                employee.getId(), employee.getSystemId(), employee.getName());
    }

    public void saveChatsEmployees(Long chatId, Integer employeeId) {
        jdbc.update("insert into chats_employees_tg(chat_id, employee_id) values (?, ?)",
                chatId, employeeId);
    }

    public List<Employee> employeesByChat(Long id) {
        return jdbc.query("select * from employee_tg as u left join chats_employees_tg ce on ce.employee_id = u.id where ce.chat_id = ?",
                (rs, row) -> {
                    return new Employee(rs.getInt("id"), rs.getInt("system_id"), rs.getString("name"));
                    }, id);
    }

    public List<Employee> employeesByChatGroupByName(Long id) {
        return jdbc.query("select name from employee_tg as u left join chats_employees_tg ce on ce.employee_id = u.id where ce.chat_id = ? group by name",
                (rs, row) -> {
                    return new Employee(rs.getString("name"));
                }, id);
    }

    public Double hoursFromToByUser(LocalDate from, LocalDate to, Integer userId) {
        return jdbc.query("select sum (hours) from time_entries where (spent_on BETWEEN ? AND ?) and user_id = ?", (rs, row) -> {
            return rs.getDouble("sum");
        }, from, to, userId).stream().findFirst().orElse(0.0);
    }

    public List<Employee> employeeByName(String name){
        return jdbc.query("select id,system_id, name from employee_tg where name = ?", (rs, row) -> {
            return new Employee(rs.getInt("id"), rs.getInt("system_id"), rs.getString("name"));
        }, name);
    }
    public List<Employee> employees(){
        return jdbc.query("select * from users ",
                (rs, row) -> {
                    return new Employee(rs.getInt("id"), rs.getInt("system_id"), rs.getString("name"));
                });
    }

    public void deleteEmployee(Integer UserId, Long ChatId) {
        jdbc.update("delete from chats_employees_tg where employee_id = (?) and chat_id = (?)",
                UserId, ChatId);
    }
}
