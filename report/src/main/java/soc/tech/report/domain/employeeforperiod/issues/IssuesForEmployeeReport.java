package soc.tech.report.domain.employeeforperiod.issues;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssuesForEmployeeReport {

    private int id;
    private int systemId;
    private String name;
    private int userId;
    private String comments;
    private double hours;

}
