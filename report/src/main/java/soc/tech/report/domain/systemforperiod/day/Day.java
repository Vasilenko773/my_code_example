package soc.tech.report.domain.systemforperiod.day;

import lombok.Data;
import soc.tech.report.domain.systemforperiod.employee.Employee;

import java.util.ArrayList;
import java.util.List;

@Data
public class Day {

    private int id;
    private String date;
    private double hours;
    private List<Employee> employees = new ArrayList<>();

    public Day(String date, double hours, List<Employee> employees) {
        this.date = date;
        this.hours = hours;
        this.employees = employees;
    }
}
