package soc.tech.report.domain.systemforperiod.employee;

import lombok.Data;

@Data
public class Employee {

    private int id;
    private int systemId;
    private int userId;
    private String name;
    private String issues;
    private String issuesName;
    private String comment;
    private double hours;

    public Employee(int systemId, int userId, String name, String issues, String issuesName, String comment, double hours) {
        this.systemId = systemId;
        this.userId = userId;
        this.name = name;
        this.issues = issues;
        this.issuesName = issuesName;
        this.comment = comment;
        this.hours = hours;
    }
}
