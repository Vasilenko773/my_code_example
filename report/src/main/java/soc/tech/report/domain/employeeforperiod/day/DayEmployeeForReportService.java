package soc.tech.report.domain.employeeforperiod.day;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;
import soc.tech.report.domain.employeeforperiod.issues.IssuesEmployeeReportService;
import soc.tech.report.domain.employeeforperiod.issues.IssuesForEmployeeReport;
import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DayEmployeeForReportService {

    private final IssuesEmployeeReportService issuesEmployeeReportService;
    private final DateFormatBuilder dateFormatBuilder;

    public List<DayEmployeeForReport> daysForPeriod(LocalDate from, LocalDate to, int userId) {
        List<DayEmployeeForReport> days = new ArrayList<>();
        int i = 1;
        while (!from.isEqual(to.plusDays(1))) {
            List<IssuesForEmployeeReport> issues = issuesEmployeeReportService.issuesForPeriod(from, from, userId);
            if (!issues.isEmpty()) {
                double sum = issues.stream().mapToDouble(IssuesForEmployeeReport::getHours).sum();
                double hours = BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                String date = dateFormatBuilder.dateByFormat(new ReportDate(from, "dd.MM.yyyy"));
                days.add(new DayEmployeeForReport(i++, date, hours, issues));
            }
            from = from.plusDays(1);
        }
        return days;
    }
}
