package soc.tech.redmine_data.web.issues;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soc.tech.redmine_data.web.issues.components.TimeTracking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IssuesWebRepository {
    private final IssuesWebAdapter issuesWebAdapter;

    public List<Issues> findAllIssuesByRequestByProject(int project, String request) {
        return issuesWebAdapter.issuesListByProject(project).stream()
                .filter(i -> i.getTracker().getName().equals(request))
                .filter(i -> i.getClosed() == null)
                .collect(Collectors.toList());
    }

    public List<Issues> filterByDeadline(List<Issues> issuesList, int days, int limit) {
        List<Issues> filterList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Issues issues : issuesList) {
            for (TimeTracking timeTracking : issues.getCustomFields()) {
                if (timeTracking.getName().equals("Срок сдачи Заказчику")
                        && timeTracking.getValue() != null && !timeTracking.getValue().equals("")) {

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate deadline = LocalDate.parse(timeTracking.getValue(), dtf);
                    if (deadline.minusDays(days).isBefore(today) && (deadline.minusDays(limit).isAfter(today)) ||
                            deadline.minusDays(limit).equals(today)) {
                        filterList.add(issues);
                    }
                }
            }
        }
        return filterList;
    }

    public List<Issues> filterByDueDateNotNullAndDueDateIsOverdue(List<Issues> issuesList) {
        return issuesList.stream()
                .filter(i -> i.getDueDate() != null)
                .filter(i -> i.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public Issues issuesById(String id) {
        return issuesWebAdapter.issuesBuId(id);
    }

    public List<Issues> filterByDeadLineNotEmpty(List<Issues> issuesList, String fieldName) {
        List<Issues> filterIssues = new ArrayList<>();
        for (Issues issues : issuesList) {
            for (TimeTracking t : issues.getCustomFields()) {
                if (t.getName().equals(fieldName) && t.getValue() != null && !t.getValue().equals("")) {
                    filterIssues.add(issues);
                }
            }
        }
        return filterIssues;
    }

    public List<Issues> allIssues(int offset) {
        return issuesWebAdapter.allIssuesList(offset);
    }

    public JsonDataIssues issuesInfo() {
        return issuesWebAdapter.issuesInfo();
    }
}

