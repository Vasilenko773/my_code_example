package soc.tech.report.domain.systemforperiod.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.issues.IssuesDb;
import soc.tech.data.repository.db.issues.IssuesDbRepository;
import soc.tech.data.repository.db.timeentries.TimeEntriesDb;
import soc.tech.data.repository.db.timeentries.TimeEntriesDbRepository;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;
import soc.tech.data.repository.db.timeentries.components.user.UserDbRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final TimeEntriesDbRepository timeEntriesDbRepository;
    private final IssuesDbRepository issuesDbRepository;
    private final UserDbRepository userDbRepository;

    public List<Employee> employeesByProjectFromToForDb(int project, LocalDate from, LocalDate to) {
        List<Employee> employees = new ArrayList<>();
        List<TimeEntriesDb> timeEntry = new ArrayList<>();
        timeEntry = timeEntriesDbRepository.timeEntriesFromToByProject(from, to, project);
        if (!timeEntry.isEmpty()) {
            for (var i : timeEntry) {
                IssuesDb issues = issuesDbRepository.issuesById(i.getIssuesId());
                String name = issues.getName() == null ? "" : issues.getName();
                String issueId = issues.getIssueId() == null ? "0" : issues.getIssueId().toString();
                UserDb user = userDbRepository.userById(i.getUserId());
                double hours = BigDecimal.valueOf(i.getHours()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                employees.add(new Employee(user.getSystemId(), user.getUserId(), user.getName(), issueId, name, i.getComments(), hours));
            }
        }
        employees.sort(Comparator.comparing(Employee::getName));
        assignId(employees);
        return employees;
    }

    private void assignId(List<Employee> employees) {
        int i = 0;
        for (Employee employee : employees) {
            employee.setId(++i);
        }
    }
}
