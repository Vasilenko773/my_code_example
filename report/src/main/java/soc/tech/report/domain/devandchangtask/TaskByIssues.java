package soc.tech.report.domain.devandchangtask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskByIssues {

    private Integer id;
    private Integer issueId;
    private String name;
    private String status;
    private Integer userId;
    private String priority;
    private LocalDate startDate;
}
