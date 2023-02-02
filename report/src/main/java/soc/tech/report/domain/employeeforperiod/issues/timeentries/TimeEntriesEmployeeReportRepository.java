package soc.tech.report.domain.employeeforperiod.issues.timeentries;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeEntriesEmployeeReportRepository {

    private final JdbcTemplate jdbc;


    public List<TimeEntriesForEmployeeReport> timeEntriesFromToForEmployeePeriods(LocalDate from, LocalDate to, int userId) {

        return jdbc.query("select time_entriesid, system_id, project_id, issues_id, user_id, hours, comments, spent_on, " +
                "created from time_entries where (spent_on BETWEEN ? AND ?) and user_id = ?", (rs, row) -> {
            return new TimeEntriesForEmployeeReport(rs.getInt("time_entriesid"), rs.getInt("system_id"),
                    rs.getInt("project_id"), rs.getInt("issues_id"), rs.getInt("user_id"),
                    rs.getDouble("hours"), rs.getString("comments"), rs.getDate("spent_on").toLocalDate(),
                    rs.getString("created"));
        }, from, to, userId);
    }
}
