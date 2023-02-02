package soc.tech.data.repository.db.timeentries.components.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectDb {

    private Integer id;
    private Integer projectId;
    private Integer systemId;
    private String name;

    public ProjectDb(Integer id) {
        this.id = id;
    }

    public ProjectDb(Integer projectId, Integer systemId, String name) {
        this.projectId = projectId;
        this.systemId = systemId;
        this.name = name;
    }
}
