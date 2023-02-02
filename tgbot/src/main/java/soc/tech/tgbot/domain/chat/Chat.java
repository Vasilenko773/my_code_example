package soc.tech.tgbot.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soc.tech.tgbot.domain.employee.Employee;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

private Long id;

private List<Employee> employees = new ArrayList<>();
}
