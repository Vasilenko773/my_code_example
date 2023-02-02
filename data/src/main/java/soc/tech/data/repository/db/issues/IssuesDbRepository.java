package soc.tech.data.repository.db.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IssuesDbRepository {

    private final JdbcTemplate jdbc;

    public IssuesDb issuesByIssueIdAndSystemId(int issueId, int systemId) {
        return jdbc.query("select id, issueid, system_id, project_id, type, status, priority, user_id, name," +
                " description, start_date, due_date, done_ratio, estimated_hours, created, updated, closed" +
                " from issues where issueid = ? and system_id = ?", (rs, row) -> {
            return new IssuesDb(rs.getInt("id"), rs.getInt("issueid"), rs.getInt("system_id"), rs.getInt("project_id"),
                    rs.getString("type"), rs.getString("status"), rs.getString("priority"),
                    rs.getInt("user_id"), rs.getString("name"),
                    rs.getString("description") == null ? null : rs.getString("description"),
                    rs.getDate("start_date") == null ? null : rs.getDate("start_date").toLocalDate(),
                    rs.getDate("due_date") == null ? null : rs.getDate("due_date").toLocalDate(),
                    rs.getInt("done_ratio"), rs.getDouble("estimated_hours"),
                    new ArrayList<>(), rs.getString("created"), rs.getString("updated"),
                    rs.getString("closed"));
        }, issueId, systemId).stream().findFirst().orElse(new IssuesDb());
    }

    public IssuesDb issuesById(int id) {
        return jdbc.query("select id, issueid, system_id, project_id, type, status, priority, user_id, name," +
                " description, start_date, due_date, done_ratio, estimated_hours, created, updated, closed" +
                " from issues where id = ? ", (rs, row) -> {
            return new IssuesDb(rs.getInt("id"), rs.getInt("issueid"), rs.getInt("system_id"), rs.getInt("project_id"),
                    rs.getString("type"), rs.getString("status"), rs.getString("priority"),
                    rs.getInt("user_id"), rs.getString("name"),
                    rs.getString("description") == null ? null : rs.getString("description"),
                    rs.getDate("start_date") == null ? null : rs.getDate("start_date").toLocalDate(),
                    rs.getDate("due_date") == null ? null : rs.getDate("due_date").toLocalDate(),
                    rs.getInt("done_ratio"), rs.getDouble("estimated_hours"),
                    new ArrayList<>(), rs.getString("created"), rs.getString("updated"),
                    rs.getString("closed"));
        }, id).stream().findFirst().orElse(new IssuesDb());
    }

    public void save(IssuesDb issues) {
        jdbc.update("insert into issues(issueid, system_id, project_id, type, status, priority, user_id, name," +
                        " description, start_date, due_date, done_ratio, estimated_hours, created, updated, closed)" +
                        " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                issues.getIssueId(), issues.getSystemId(), issues.getProjectId(), issues.getType(), issues.getStatus(), issues.getPriority(),
                issues.getUserId(), issues.getName(), issues.getDescription(), issues.getStartDate(), issues.getDueDate(),
                issues.getDoneRatio(), issues.getEstimatedHours(), issues.getCreated(), issues.getUpdated(), issues.getClosed());
    }

    public void update(IssuesDb issues) {
        jdbc.update("update issues set (project_id, type, status, priority, user_id, name, description, start_date, due_date," +
                        " done_ratio, estimated_hours, created, updated, closed) = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                        " where issueid = ? and system_id = ?",
                issues.getProjectId(), issues.getType(), issues.getStatus(), issues.getPriority(),
                issues.getUserId(), issues.getName(), issues.getDescription(), issues.getStartDate(), issues.getDueDate(),
                issues.getDoneRatio(), issues.getEstimatedHours(), issues.getCreated(), issues.getUpdated(), issues.getClosed(),
                issues.getIssueId(), issues.getSystemId());
    }

    public List<IssuesDb> allIssues() {
        return jdbc.query("select * from issues",
                (rs, row) -> {
                    return new IssuesDb(rs.getInt("id") ,rs.getInt("issueid"), rs.getInt("system_id"), rs.getInt("project_id"),
                            rs.getString("type"), rs.getString("status"), rs.getString("priority"),
                            rs.getInt("user_id"), rs.getString("name"),
                            rs.getString("description"), rs.getDate("start_date").toLocalDate(),
                            rs.getDate("due_date") == null ? null : rs.getDate("due_date").toLocalDate(),
                            rs.getInt("done_ratio"), rs.getDouble("estimated_hours"),
                            new ArrayList<>(), rs.getString("created"), rs.getString("updated"),
                            rs.getString("closed"));
                });
    }
}
