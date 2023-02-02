package soc.tech.tgbot.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer id;
    private Integer systemId;
    private String name;
    private Double hours;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(Integer id, Integer systemId, String name) {
        this.id = id;
        this.systemId = systemId;
        this.name = name;
    }

    public Employee(String name) {
        this.name = name;
    }
}
