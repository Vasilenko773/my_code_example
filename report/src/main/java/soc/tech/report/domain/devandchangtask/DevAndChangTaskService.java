package soc.tech.report.domain.devandchangtask;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.issues.component.CustomField;
import soc.tech.data.repository.db.issues.component.CustomFieldDbRepository;
import soc.tech.data.repository.db.timeentries.components.user.UserDbRepository;
import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class DevAndChangTaskService {

    private final DevAndChangTaskRepository devAndChangTaskRepository;
    private final CustomFieldDbRepository customFieldDbRepository;
    private final UserDbRepository userDbRepository;
    private final DateFormatBuilder dateFormatBuilder;

    public List<DevAndChangTask> tasksForLimit(int days, int limit) {
       return devAndChangTaskRepository.filterByDeadline(devAndChangTasks(), days, limit);
    }


    public List<DevAndChangTask> validTasksTwoWeeks() {
        return devAndChangTaskRepository.filterByDeadline(devAndChangTasks(), 15, 8);
    }

    public List<DevAndChangTask> validTasksOneWeeks() {
        return devAndChangTaskRepository.filterByDeadline(devAndChangTasks(), 8, 0);
    }

   public List<DevAndChangTask> devAndChangTasks() {
        List<DevAndChangTask> devAndChangTasks = new ArrayList<>();
        List<TaskByIssues> taskByIssues = devAndChangTaskRepository.devAndChangeTasksByIssues();
        for (var task : taskByIssues) {
//todo Передается 0 в метод deadLine так как пока что запросы на развитие и запросы на изменение есть только в Redmine
            LocalDate deadline = deadLine(task.getId(), 0);
            if (deadline != null) {
                //todo Передается 0 в метод deadLine так как пока что запросы на развитие и запросы на изменение есть только в Redmine
                String user = task.getUserId() == null ? "" : userDbRepository.userById(task.getUserId()).getName();
                devAndChangTasks.add(new DevAndChangTask(task.getIssueId(), task.getName(), task.getStatus(), user, task.getPriority(),
                        dateFormatBuilder.dateByFormat(new ReportDate(task.getStartDate(), "dd.MM.yyyy")),
                        dateFormatBuilder.dateByFormat(new ReportDate(deadline, "dd.MM.yyyy"))));
            }
        }
        return devAndChangTasks;
    }

    private LocalDate deadLine(int taskId, int systemId) {
        List<CustomField> customFields = customFieldDbRepository.customFieldByIssuesId(taskId, systemId);
        if (!customFields.isEmpty()) {
            for (var field : customFields) {
                if (field.getName().equals("Срок сдачи Заказчику") && field.getValue() != null) {
                    return LocalDate.parse(field.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
        }
        return null;
    }
}