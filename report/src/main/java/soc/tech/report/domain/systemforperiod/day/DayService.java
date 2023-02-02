package soc.tech.report.domain.systemforperiod.day;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.report.domain.systemforperiod.employee.Employee;
import soc.tech.report.domain.systemforperiod.employee.EmployeeService;
import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayService {

    private final EmployeeService employeeService;
    private final DateFormatBuilder dateFormatBuilder;

    public List<Day> daysByProjectFromToSortByDateForDb(int project, LocalDate from, LocalDate to) {
        List<Day> days = new ArrayList<>();
        LocalDate start = from;
        while (!start.isEqual(to.plusDays(1))) {
            List<Employee> employees = employeeService.employeesByProjectFromToForDb(project, start, start);
            if (!employees.isEmpty()) {
                double sum = employees.stream().mapToDouble(Employee::getHours).sum();
                double hours = BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                String date = dateFormatBuilder.dateByFormat(new ReportDate(start, "dd.MM.yyyy"));
                days.add(new Day(date, hours, employees));
            }
            start = start.plusDays(1);
        }
        days.sort(Comparator.comparing(Day::getDate).reversed());
        assignId(days);
        return days;
    }

    private void assignId(List<Day> days) {
        int i = 0;
        for (Day day : days) {
            day.setId(++i);
        }
    }

}
