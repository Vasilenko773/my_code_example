package soc.tech.report.domain.employeeforperiod.issues.timeentries;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TimeEntriesForEmployeeReport {

    private Integer id;
    private Integer systemId;
    private Integer projectId;
    private Integer issuesId;
    private Integer userId;
    private Double hours;
    private String comments;
    private LocalDate spentOn;
    private String created;


}
