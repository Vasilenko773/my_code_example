package soc.tech.report.domain.missingduedate;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MissingDueDate {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String status;
    private String priority;
    private String executor;
    private String project;
    private String startDate;

}
