package soc.tech.data.repository.db.timeentries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeEntriesDb {

    private Integer id;
    private Integer timeEntriesId;
    private Integer systemId;
    private Integer projectId;
    private Integer issuesId;
    private Integer userId;
    private Integer activityId;
    private double hours;
    private String comments;
    private LocalDate spentOn;
    private String created;
    private String updated;

    public TimeEntriesDb(Integer timeEntriesId, Integer systemId, Integer projectId, Integer issuesId,
                         Integer userId, double hours, String comments, LocalDate spentOn,
                         String created, String updated) {
        this.timeEntriesId = timeEntriesId;
        this.systemId = systemId;
        this.projectId = projectId;
        this.issuesId = issuesId;
        this.userId = userId;
        this.hours = hours;
        this.comments = comments;
        this.spentOn = spentOn;
        this.created = created;
        this.updated = updated;
    }

    public TimeEntriesDb(Integer id, Integer systemId, Integer userId) {
        this.id = id;
        this.systemId = systemId;
        this.userId = userId;
    }
}
