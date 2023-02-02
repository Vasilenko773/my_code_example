package soc.tech.data.repository.db.timeentries.components.project;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectDbRepository {

    private final JdbcTemplate jdbc;

    public ProjectDb projectByProjectIdAndSystemId(int projectId, int systemId) {
        return jdbc.query("select id, projectid, system_id, name from projects where projectid = ? and system_id = ?",
                (rs, row) -> {
            return new ProjectDb(rs.getInt("id"), rs.getInt("projectid"), rs.getInt("system_id"),
                    rs.getString("name"));
        }, projectId, systemId).stream().findFirst().orElse(new ProjectDb());
    }

    public ProjectDb projectById(int id) {
        return jdbc.query("select id, projectid, system_id, name from projects where id = ? ",
                (rs, row) -> {
                    return new ProjectDb(rs.getInt("id"), rs.getInt("projectid"), rs.getInt("system_id"),
                            rs.getString("name"));
                }, id).stream().findFirst().orElse(new ProjectDb());
    }

    public void save(ProjectDb project) {
        jdbc.update("insert into projects(projectid, system_id, name) values (?, ?, ?)",
               project.getProjectId(), project.getSystemId(), project.getName());
    }

    public void update(ProjectDb project) {
        jdbc.update("update projects set name = ? where projectid = ? and system_id = ?",
                project.getName(), project.getProjectId(), project.getSystemId());
    }

    public List<ProjectDb> projects() {
        return jdbc.query("select * from projects",
                (rs, row) -> {
                    return new ProjectDb(rs.getInt("id"), rs.getInt("projectid"),
                            rs.getInt("system_id"), rs.getString("name"));
                });
    }
}
