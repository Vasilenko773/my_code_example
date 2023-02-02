package soc.tech.report.domain.incident;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncidentByIssue {

    private Integer id;
    private String name;
    private Integer projectId;
    private String status;
    private String priority;
    private Integer userId;
    private LocalDate startDate;
    private LocalDate dueDate;

}
