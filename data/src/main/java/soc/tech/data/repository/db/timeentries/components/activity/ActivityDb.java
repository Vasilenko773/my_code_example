package soc.tech.data.repository.db.timeentries.components.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActivityDb {

    private Integer id;
    private Integer systemId;
    private String name;

    public ActivityDb(Integer id) {
        this.id = id;
    }
}
