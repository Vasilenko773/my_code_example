package soc.tech.report.domain.incident;

import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Incident {


    @Id
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String project;
    private String status;
    private String priority;
    private String executor;
    private String startDate;
    private String dueDate;

    public Incident(Integer id, String name, String status, String priority, String executor, String startDate, String dueDate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.executor = executor;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }


}
