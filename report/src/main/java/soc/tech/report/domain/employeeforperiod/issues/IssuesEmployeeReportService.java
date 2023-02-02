package soc.tech.report.domain.employeeforperiod.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.issues.IssuesDb;
import soc.tech.data.repository.db.issues.IssuesDbRepository;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;
import soc.tech.data.repository.db.timeentries.components.user.UserDbService;
import soc.tech.report.domain.employeeforperiod.issues.timeentries.TimeEntriesEmployeeReportRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssuesEmployeeReportService {
    private final IssuesDbRepository issuesDbRepository;
    private final UserDbService userDbService;
    private final TimeEntriesEmployeeReportRepository timeEntriesEmployeeReportRepository;

    public List<IssuesForEmployeeReport> issuesForPeriod(LocalDate from, LocalDate to, int userId) {
        List<IssuesForEmployeeReport> issues = new ArrayList<>();
        var timeEntries = timeEntriesEmployeeReportRepository
                .timeEntriesFromToForEmployeePeriods(from, to, userId);
        if (!timeEntries.isEmpty()) {
            for (var t : timeEntries) {
                IssuesDb issuesDb = t.getIssuesId() == null ? new IssuesDb() : issuesDbRepository.issuesById(t.getIssuesId());
                String name = issuesDb.getName();
                double hours = BigDecimal.valueOf(t.getHours()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                String comments = t.getComments() == null ? "" : t.getComments();
                int id = issuesDb.getIssueId() == null ? 0 : issuesDb.getIssueId();
                int user = t.getUserId() == null ? 0 :userDbService.userById(t.getUserId()).getUserId();
                issues.add(new IssuesForEmployeeReport(id, t.getSystemId(), name, user, comments, hours));
            }
        }
        return issues;
    }
}


