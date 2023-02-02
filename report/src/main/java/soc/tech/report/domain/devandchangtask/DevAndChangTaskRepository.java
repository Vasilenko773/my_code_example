package soc.tech.report.domain.devandchangtask;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DevAndChangTaskRepository {

    private final JdbcTemplate jdbc;

    public List<DevAndChangTask> filterByDeadline(List<DevAndChangTask> devTasks, int days, int limit) {
        List<DevAndChangTask> filterChangeTasks = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate today = LocalDate.now();
        for (var devTask : devTasks) {
            LocalDate deadline = LocalDate.parse(devTask.getDeadline(), dtf);
            if (deadline.minusDays(days).isBefore(today) && (deadline.minusDays(limit).isAfter(today)) ||
                    deadline.minusDays(limit).equals(today)) {
                filterChangeTasks.add(devTask);
            }
        }
        return filterChangeTasks;
    }

    public List<TaskByIssues> devAndChangeTasksByIssues() {
        return jdbc.query("select * from issues where (type = 'Запрос на развитие' or type = 'Запрос на изменение') and closed is null",
                (rs, row) -> {
                    return new TaskByIssues(rs.getInt("id"), rs.getInt("issueid"), rs.getString("name"),
                            rs.getString("status"), rs.getInt("user_id"), rs.getString("priority"),
                            rs.getDate("start_date").toLocalDate());
                });
    }
}
