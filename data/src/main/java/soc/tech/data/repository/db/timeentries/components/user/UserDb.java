package soc.tech.data.repository.db.timeentries.components.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDb {

    private Integer id;
    private Integer userId;
    private Integer systemId;
    private String name;

    public UserDb(Integer userId) {
        this.userId = userId;
    }

    public UserDb(Integer userId, Integer systemId, String name) {
        this.userId = userId;
        this.systemId = systemId;
        this.name = name;
    }
}
