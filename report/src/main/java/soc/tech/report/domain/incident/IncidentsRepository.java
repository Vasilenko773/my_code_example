package soc.tech.report.domain.incident;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IncidentsRepository {
    private final JdbcTemplate jdbc;

    public List<IncidentByIssue> incidentsByIssues() {
        return jdbc.query("select * from issues where type = 'Инцидент' and closed is null",
                (rs, row) -> {
                    return new IncidentByIssue(rs.getInt("issueid"), rs.getString("name"), rs.getInt("project_id"),
                            rs.getString("status"), rs.getString("priority"),
                            rs.getInt("user_id"), rs.getDate("start_date").toLocalDate(),
                            rs.getDate("due_date") == null ? null : rs.getDate("due_date").toLocalDate());
                });
    }
}
