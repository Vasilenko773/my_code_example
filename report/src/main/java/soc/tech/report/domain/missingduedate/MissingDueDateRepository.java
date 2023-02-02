package soc.tech.report.domain.missingduedate;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDb;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissingDueDateRepository {

    private final JdbcTemplate jdbc;

    public List<MissingDueDate> findAllMissingDueDates() {
        return jdbc.query("select * from issues where (estimated_hours <= 0 or estimated_hours is null) and closed" +
                        " is null and system_id = 0;",
                (rs, row) -> {
                    return new MissingDueDate(rs.getInt("issueid"), rs.getString("name"),
                            rs.getString("status"), rs.getString("priority"),
                            userByIssues(rs.getInt("user_id")).getName(),
                            projectByIssues(rs.getInt("project_id")).getName(), rs.getString("start_date"));
                });
    }
    private UserDb userByIssues(Integer id) {
        return jdbc.query("select id, userid, system_id, name from users where id = ?", (rs, row) -> {
            return new UserDb(rs.getInt("id"), rs.getInt("userid"),
                    rs.getInt("system_id"), rs.getString("name"));
        }, id).stream().findFirst().orElse(new UserDb());
    }

    private ProjectDb projectByIssues(Integer projectId) {
        return jdbc.query("select id, projectid, system_id, name from projects where id = ?", (rs, row) -> {
            return new ProjectDb(rs.getInt("id"), rs.getInt("projectid"),
                    rs.getInt("system_id"), rs.getString("name"));
        }, projectId).stream().findFirst().orElse(new ProjectDb());
    }
}