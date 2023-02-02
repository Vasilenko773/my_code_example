package soc.tech.data.repository.db.timeentries;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeEntriesDbRepository {
    private final JdbcTemplate jdbc;
    public void save(TimeEntriesDb interval) {
        jdbc.update("insert into time_entries(time_entriesid, system_id, project_id, issues_id, user_id, activity_id, hours, comments," +
                        "spent_on, created, updated) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                interval.getTimeEntriesId(), interval.getSystemId(), interval.getProjectId(),
                interval.getIssuesId(), interval.getUserId(), interval.getActivityId(),
                interval.getHours(), interval.getComments(),
                interval.getSpentOn(), interval.getCreated(), interval.getUpdated());
    }

    public void update(TimeEntriesDb interval) {
        jdbc.update("update time_entries set (project_id, issues_id, user_id, activity_id," +
                        " hours, comments, spent_on, created, updated) = (?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                        " where time_entriesid = ? and system_id = ?",
                interval.getProjectId(), interval.getIssuesId(), interval.getUserId(), interval.getActivityId(),
                interval.getHours(), interval.getComments(), interval.getSpentOn(), interval.getCreated(),
                interval.getUpdated(), interval.getTimeEntriesId(), interval.getSystemId());
    }

    public List<TimeEntriesDb> intervals() {
        return jdbc.query("select * from time_entries",
                (rs, row) -> {
                    return new TimeEntriesDb(rs.getInt("id"), rs.getInt("time_entriesid"), rs.getInt("system_id"), rs.getInt("project_id"),
                            rs.getInt("issues_id"),rs.getInt("user_id"), rs.getInt("activity_id"),
                            rs.getDouble("hours"), rs.getString("comments"), rs.getDate("spent_on").toLocalDate(),
                            rs.getString("created"), rs.getString("updated"));
                });
    }

    public TimeEntriesDb intervalById(int timeEntriesId, int systemId) {
        return jdbc.query("select time_entriesid, system_id, project_id, issues_id, user_id, " +
                " hours, comments, spent_on, created, updated from time_entries where time_entriesid = ? and system_id = ?", (rs, row) -> {
            return new TimeEntriesDb(rs.getInt("time_entriesid"), rs.getInt("system_id"), rs.getInt("project_id"),
                    rs.getInt("issues_id"),rs.getInt("user_id"), rs.getDouble("hours"),
                    rs.getString("comments"), rs.getDate("spent_on").toLocalDate(),
                    rs.getString("created"), rs.getString("updated"));
        }, timeEntriesId, systemId).stream().findFirst().orElse(new TimeEntriesDb());
    }

    public List<TimeEntriesDb> timeEntriesFromToByProject(LocalDate from, LocalDate to, Integer projectId) {

        return jdbc.query("select time_entriesid, system_id, project_id, issues_id, user_id, activity_id, hours, comments, spent_on, " +
                "created, updated from time_entries where (spent_on BETWEEN ? AND ?) and project_id = ?", (rs, row) -> {
            return new TimeEntriesDb(rs.getInt("time_entriesid"), rs.getInt("system_id"), rs.getInt("project_id"),
                    rs.getInt("issues_id"),rs.getInt("user_id"), rs.getDouble("hours"), rs.getString("comments"), rs.getDate("spent_on").toLocalDate(),
                    rs.getString("created"), rs.getString("updated"));
        }, from, to, projectId);
    }

    public List<TimeEntriesDb> timeEntriesFromTo(LocalDate from, LocalDate to) {

        return jdbc.query("select time_entriesid, system_id, project_id, issues_id, user_id, activity_id, hours, comments, spent_on, " +
                "created, updated from time_entries where (spent_on BETWEEN ? AND ?)", (rs, row) -> {
            return new TimeEntriesDb(rs.getInt("time_entriesid"), rs.getInt("system_id"), rs.getInt("project_id"),
                    rs.getInt("issues_id"),rs.getInt("user_id"), rs.getDouble("hours"), rs.getString("comments"), rs.getDate("spent_on").toLocalDate(),
                    rs.getString("created"), rs.getString("updated"));
        }, from, to);
    }

    public List<Integer> usersIdByTimeEntriesFromPeriod(LocalDate from, LocalDate to) {
        return jdbc.query("select  user_id  from time_entries where (spent_on BETWEEN ? AND ?)", (rs, row) -> {
            return rs.getInt("user_id");
        }, from, to);
    }

    public void deleteFromToByRedmine(LocalDate from, LocalDate to) {
        jdbc.update("delete from time_entries where system_id = 0 and (spent_on BETWEEN ? AND ?)", from, to);
    }

    public void deleteFromToByBitrix(LocalDate from, LocalDate to) {
        jdbc.update("delete from time_entries where system_id = 1 and (spent_on BETWEEN ? AND ?)", from, to);
    }
}


