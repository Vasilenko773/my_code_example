package soc.tech.report.domain.employeeforperiod.day;

import lombok.AllArgsConstructor;
import lombok.Data;
import soc.tech.report.domain.employeeforperiod.issues.IssuesForEmployeeReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class DayEmployeeForReport {

    private int id;
    private String date;
    private double hours;
    private List<IssuesForEmployeeReport> issues = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DayEmployeeForReport that = (DayEmployeeForReport) o;
        return id == that.id && Double.compare(that.hours, hours) == 0 && Objects.equals(date, that.date) && Objects.equals(issues, that.issues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }
}
