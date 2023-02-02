package soc.tech.data.repository.db.timeentries.components.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ActivityDbRepository {

    private final JdbcTemplate jdbc;

    public ActivityDb activityById(int id) {
        return jdbc.query("select id, system_id, name from activity where id = ?", (rs, row) -> {
            return new ActivityDb(rs.getInt("id"), rs.getInt("system_id"), rs.getString("name"));
        }, id).stream().findFirst().orElse(new ActivityDb());
    }

    public void save(ActivityDb activity) {
        jdbc.update("insert into activity(id, system_id, name) values (?, ?, ?)",
                activity.getId(), activity.getSystemId(), activity.getName());
    }

    public void update(ActivityDb activity) {
        jdbc.update("update activity set name = ? where id = ?",
                activity.getName(), activity.getId());
    }

    public List<ActivityDb> activities() {
        return jdbc.query("select * from projects",
                (rs, row) -> {
                    return new ActivityDb(rs.getInt("id"), rs.getInt("system_id"), rs.getString("name"));
                });
    }

}
