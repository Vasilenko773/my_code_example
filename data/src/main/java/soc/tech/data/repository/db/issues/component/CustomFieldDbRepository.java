package soc.tech.data.repository.db.issues.component;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomFieldDbRepository {

    private final JdbcTemplate jdbc;

    public void save(CustomField field) {
        jdbc.update("insert into custom_fields(system_id, name, value, issues_id) values (?, ?, ?, ?)",
                field.getSystemId(), field.getName(), field.getValue(), field.getIssuesId());
    }

    public void update(CustomField field) {
        jdbc.update("update custom_fields set (name, value, issues_id) = (?, ?, ?)" +
                        " where issues_id = ? and system_id = ?",
                field.getName(), field.getValue(), field.getIssuesId(), field.getIssuesId(), field.getSystemId());
    }

    public List<CustomField> customFieldByIssuesId(int issueId, int systemId) {
        return new ArrayList<>(jdbc.query("select * from custom_fields where issues_id = ? and system_id = ?",
                (rs, row) -> {
                    return new CustomField(rs.getInt("id"), rs.getInt("system_id"), rs.getString("name"),
                            rs.getString("value"), rs.getInt("issues_id"));
                }, issueId, systemId));
    }

}
