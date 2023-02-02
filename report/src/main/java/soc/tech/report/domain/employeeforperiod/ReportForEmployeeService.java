package soc.tech.report.domain.employeeforperiod;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.timeentries.TimeEntriesDbRepository;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;
import soc.tech.data.repository.db.timeentries.components.user.UserDbRepository;
import soc.tech.report.domain.employeeforperiod.day.DayEmployeeForReport;
import soc.tech.report.domain.employeeforperiod.day.DayEmployeeForReportService;
import soc.tech.report.domain.employeeforperiod.issues.IssuesForEmployeeReport;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportForEmployeeService {

    private final DayEmployeeForReportService dayEmployeeForReportService;
    private final UserDbRepository userDbRepository;
    private final TimeEntriesDbRepository timeEntriesDbRepository;

    public List<ReportForEmployee> employeesForPeriod(LocalDate from, LocalDate to) {
        List<ReportForEmployee> rowReport = new ArrayList<>();
        from = from == null ? startDateMonday() : from;
        to = to == null ? LocalDate.now().plusDays(1) : to;

        Set<Integer> usersId = new HashSet<>(timeEntriesDbRepository.usersIdByTimeEntriesFromPeriod(from, to));
        Map<String, ReportForEmployee> buffer = new HashMap<>();
        for (var i : usersId) {
            List<DayEmployeeForReport> days = dayEmployeeForReportService.daysForPeriod(from, to, i);
            if (!days.isEmpty()) {
                UserDb user = userDbRepository.userById(i);
                double hours = hours(days);
                String name = name(user);
                ReportForEmployee employee = new ReportForEmployee(name, hours, days);
                if (buffer.containsKey(name)) {
                    employee = packingDays(buffer.get(name), days);
                }
//                rowReport.add(employee);
                buffer.put(name, employee);
            }
        }
        buffer.forEach((k, v) -> rowReport.add(v));
        List<ReportForEmployee> rsl = rowReport.stream().
                sorted(Comparator.comparing(ReportForEmployee::getName)).collect(Collectors.toList());

        setId(rsl);
        return rsl;
    }

    private String name(UserDb user) {
        String[] array = user.getName().split(" ");
        return array.length > 1 ? array[1].concat(" ").concat(array[0]) : array[0];
    }

    private double hours(List<DayEmployeeForReport> days) {
        double sum = days.stream().mapToDouble(DayEmployeeForReport::getHours).sum();
        return BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    //    ДОРАБОТАТЬЬ ЭТОТ МЕТОД
    private ReportForEmployee packingDays(ReportForEmployee reportForEmployee, List<DayEmployeeForReport> days) {
        List<DayEmployeeForReport> resultDay = new ArrayList<>();
        Map<String, DayEmployeeForReport> dayEmployee = mapDays(reportForEmployee);
        for (DayEmployeeForReport day : days) {
            if (dayEmployee.containsKey(day.getDate())) {
                DayEmployeeForReport dayEmployeeForReport = dayEmployee.get(day.getDate());
                dayEmployeeForReport.getIssues().addAll(day.getIssues());
                double sum = dayEmployeeForReport.getIssues().stream().mapToDouble(IssuesForEmployeeReport::getHours).sum();
                double hours = BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                dayEmployeeForReport.setHours(hours);
                resultDay.add(dayEmployeeForReport);
            } else {
                resultDay.add(day);
            }
        }
        reportForEmployee.getDays().clear();
        reportForEmployee.getDays().addAll(resultDay);
        reportForEmployee.setHours(hours(reportForEmployee.getDays()));
        return reportForEmployee;
    }

    private Map<String, DayEmployeeForReport> mapDays(ReportForEmployee reportForEmployee) {
        Map<String, DayEmployeeForReport> mapDay = new HashMap<>();
        for (DayEmployeeForReport day : reportForEmployee.getDays()) {
            mapDay.put(day.getDate(), day);
        }
        return mapDay;
    }

    private void setId(List<ReportForEmployee> reportForEmployees) {
        int i = 1;
        for (var r : reportForEmployees) {
            r.setId(i++);
        }
    }

    public LocalDate startDateMonday() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}
