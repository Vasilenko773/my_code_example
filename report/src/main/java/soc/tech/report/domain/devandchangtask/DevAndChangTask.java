package soc.tech.report.domain.devandchangtask;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("dev_change_tasks")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DevAndChangTask {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String status;
    private String executor;
    private String priority;
    private String startDate;
    private String deadline;
}

