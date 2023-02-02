package soc.tech.report.domain.duedatelessfact;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DueDateLessFactRepository {

    private final JdbcTemplate jdbc;

    public List<DueDateLessFact> findAllDueDateLessFact() {
        return jdbc.query("select * from issues where estimated_hours > 0 and closed is null",
                (rs, row) -> {
                    return new DueDateLessFact(rs.getInt("issueid"), rs.getString("name"),
                            rs.getString("status"), userByIssues(rs.getInt("user_id")).getName(),
                            rs.getString("priority"), rs.getString("start_date"),
                            rs.getString("due_date"), hours(rs.getInt("id")),
                            rs.getDouble("estimated_hours"));
                });
    }

    private UserDb userByIssues(Integer id) {
        return jdbc.query("select id, userid, system_id, name from users where id = ?", (rs, row) -> {
            return new UserDb(rs.getInt("id"), rs.getInt("userid"),
                    rs.getInt("system_id"), rs.getString("name"));
        }, id).stream().findFirst().orElse(new UserDb());
    }

    private Double hours(Integer id) {
        return jdbc.query("select sum(hours) from time_entries where issues_id = ?", (rs, row) -> {
            return rs.getDouble("sum");
        },id).stream().findFirst().orElse(0.0);
    }
}
