package soc.tech.report.domain.duedatelessfact;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DueDateLessFact {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String status;
    private String executor;
    private String priority;
    private String startDate;
    private String deadline;
    private Double hours;
    private Double estimatedHours;
}
