package soc.tech.data.repository.db.issues.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomField {

    private Integer id;
    private Integer systemId;
    private String name;
    private String value;
    private Integer IssuesId;

    public CustomField(Integer systemId, String name, String value, Integer issuesId) {
        this.systemId = systemId;
        this.name = name;
        this.value = value;
        IssuesId = issuesId;
    }
}
