package soc.tech.data.repository.db.issues;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soc.tech.data.repository.db.issues.component.CustomField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IssuesDb {

    private Integer id;
    private Integer issueId;
    private Integer systemId;
    private Integer projectId;
    private String type;
    private String status;
    private String priority;
    private Integer userId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Integer doneRatio;
    private Double estimatedHours;
    private List<CustomField> customFields = new ArrayList<>();
    private String created;
    private String updated;
    private String closed;

    public IssuesDb(Integer issueId) {
        this.issueId = issueId;
    }

    public IssuesDb(Integer issueId, Integer systemId, Integer projectId, String type, String status, String priority,
                    Integer userId, String name, String description, LocalDate startDate, LocalDate dueDate,
                    Integer doneRatio, Double estimatedHours, List<CustomField> customFields, String created,
                    String updated, String closed) {
        this.issueId = issueId;
        this.systemId = systemId;
        this.projectId = projectId;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.doneRatio = doneRatio;
        this.estimatedHours = estimatedHours;
        this.customFields = customFields;
        this.created = created;
        this.updated = updated;
        this.closed = closed;
    }
}
