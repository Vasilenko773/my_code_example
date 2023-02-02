package soc.tech.report.domain.employeeforperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import soc.tech.report.domain.employeeforperiod.day.DayEmployeeForReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ReportForEmployee {

    private int id;
    private String name;
    private double hours;
    private List<DayEmployeeForReport> days = new ArrayList<>();

    public ReportForEmployee(String name, double hours, List<DayEmployeeForReport> days) {
        this.name = name;
        this.hours = hours;
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportForEmployee that = (ReportForEmployee) o;
        return id == that.id && Double.compare(that.hours, hours) == 0 && Objects.equals(name, that.name) && Objects.equals(days, that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
