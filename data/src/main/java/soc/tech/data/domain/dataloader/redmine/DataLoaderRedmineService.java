package soc.tech.data.domain.dataloader.redmine;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.issues.IssuesDb;
import soc.tech.data.repository.db.issues.IssuesDbService;
import soc.tech.data.repository.db.issues.component.CustomField;
import soc.tech.data.repository.db.issues.component.CustomFieldDbService;
import soc.tech.data.repository.db.timeentries.TimeEntriesDb;
import soc.tech.data.repository.db.timeentries.TimeEntriesDbService;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDb;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDbService;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;
import soc.tech.data.repository.db.timeentries.components.user.UserDbService;
import soc.tech.redmine_data.web.issues.Issues;
import soc.tech.redmine_data.web.issues.IssuesWebRepository;
import soc.tech.redmine_data.web.project.Project;
import soc.tech.redmine_data.web.project.ProjectWebAdapter;
import soc.tech.redmine_data.web.timeentries.TimeEntriesWebRepository;
import soc.tech.redmine_data.web.timeentries.TimeInterval;
import soc.tech.redmine_data.web.user.User;
import soc.tech.redmine_data.web.user.UserWebAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
@EnableScheduling
@EnableAsync
public class DataLoaderRedmineService {

    private final TimeEntriesWebRepository timeEntriesWebRepository;
    private final IssuesDbService issuesDbService;
    private final IssuesWebRepository issuesWebRepository;
    private final TimeEntriesDbService timeEntriesDbService;
    private final ProjectDbService projectDbService;
    private final CustomFieldDbService customFieldDbService;
    private final UserDbService userDbService;
    private final ProjectWebAdapter projectWebAdapter;
    private final UserWebAdapter userWebAdapter;

    @Scheduled(cron = "${data.all.cron}")
    @Async
    public void saveAllElement() {
        Set<TimeInterval> timeEntryJson = allTimeEntriesAndIssuesFromRedmine();
        Set<Issues> issuesJsons = allIssuesByRedmine();
        saveAllUserDb();
        saveAllProject();
        saveAllIssuesFromRedmine(issuesJsons);
        saveAllTimeInterval(timeEntryJson);
        saveAllCustomField(issuesJsons);
    }

    @Scheduled(cron = "${data.cron}")
    @Async
    public void savaToWeeksElements() {
        Set<TimeInterval> timeEntryJson = timeIntervalsFromRedmineForToWeeks();
        Set<Issues> issuesJsons = allIssuesByRedmine();
        saveAllUserDb();
        saveAllProject();
        saveAllIssuesFromRedmine(issuesJsons);
        saveAllTimeInterval(timeEntryJson);
        saveAllCustomField(issuesJsons);
    }

    private Set<TimeInterval> allTimeEntriesAndIssuesFromRedmine() {
        Set<TimeInterval> rsl = new HashSet<>();
        int count = timeEntriesWebRepository.infoTimeEntries().getCount();
        while (count > 0) {
            count = count - 100;
            List<TimeInterval> offsetInterval = timeEntriesWebRepository.timeIntervalsOffSet(count);
            rsl.addAll(offsetInterval);
        }
        return rsl;
    }

    private Set<TimeInterval> timeIntervalsFromRedmineForToWeeks() {
        Set<TimeInterval> rsl = new HashSet<>();
        LocalDate twoWeeks = LocalDate.now().minusDays(15);
        int count = timeEntriesWebRepository.infoTimeEntriesForToday(twoWeeks, LocalDate.now()).getCount();
        while (count > 0) {
            count = count - 100;
            List<TimeInterval> offsetInterval = timeEntriesWebRepository.timeIntervalsFromToOffset(twoWeeks, LocalDate.now(), count);
            rsl.addAll(offsetInterval);
        }
        log.info("Load time entries redmine " + rsl.size());
        return rsl;
    }

    private Set<Issues> allIssuesByRedmine() {
        Set<Issues> issuesJsons = new HashSet<>();
        int count = issuesWebRepository.issuesInfo().getCount();
        while (count > 0) {
            count = count - 100;
            List<Issues> issues = issuesWebRepository.allIssues(count);
            issuesJsons.addAll(issues);
        }
        return issuesJsons;
    }

    private Set<UserDb> allUsersFromRedmine() {
        Set<UserDb> userDbs = new HashSet<>();
        int count = userWebAdapter.usersInfoActive().getCount();
        while (count > 0) {
            count = count - 100;
            List<User> users = userWebAdapter.activeUsers(count);
            userDbs.addAll(userByUserWeb(users));
        }
        int count1 = userWebAdapter.usersInfoClosed().getCount();
        while (count1 > 0) {
            count1 = count - 100;
            List<User> users = userWebAdapter.closedUsers(count1);
            userDbs.addAll(userByUserWeb(users));
        }

        return userDbs;
    }

    public List<UserDb> userByUserWeb(List<User> users) {
        List<UserDb> userDbs = new ArrayList<>();
        if (!users.isEmpty()) {
            for (User u : users) {
                userDbs.add(new UserDb(u.getId(), 0, u.getFirstname() + " " + u.getLastname()));
            }
        }
        return userDbs;
    }

    private void saveAllUserDb() {
        Set<UserDb> allUsers = allUsersFromRedmine();
        int count = allUsers.size();
        log.info("save user redmine " + count);
        for (var u : allUsers) {
            userDbService.saveOrUpdate(u);
            log.info(count = count - 1);
        }
    }

    private void saveAllProject() {
        List<Project> projects = projectWebAdapter.projects();
        int count = projects.size();
        log.info("save project redmine " + count);
        for (var p : projects) {
            ProjectDb project = new ProjectDb(p.getId(), 0, p.getName());
            projectDbService.saveOrUpdate(project);
            log.info(count = count - 1);
        }
    }

    private Set<IssuesDb> allIssuesDbFromRedmine(Set<Issues> issuesJsons) {
        Set<IssuesDb> allIssues = new HashSet<>();
        for (var i : issuesJsons) {
            Integer projectId = i.getProject() == null ? null : projectDbService.projectByProjectIdAndSystemId(i.getProject().getId(), 0).getId();
            String type = i.getTracker() == null ? null : i.getTracker().getName();
            String status = i.getStatus() == null ? null : i.getStatus().getName();
            String priority = i.getPriority() == null ? null : i.getPriority().getName();
            Integer userId = i.getAssigned() == null ? null : userDbService.userByUserIdAndSystemId(i.getAssigned().getId(), 0).getId();

            allIssues.add(new IssuesDb(i.getId(), 0, projectId, type, status, priority, userId, i.getSubject(),
                    i.getDescription(), i.getStartDate(), i.getDueDate(), i.getDoneRatio(), i.getEstimatedHours(),
                    new ArrayList<>(), i.getCreated(), i.getUpdated(), i.getClosed()));
        }
        return allIssues;
    }

    private void saveAllIssuesFromRedmine(Set<Issues> issuesJsons) {
        Set<IssuesDb> issuesDb = allIssuesDbFromRedmine(issuesJsons);
        int count = issuesDb.size();
        log.info("save issues redmine " + count);
        for (var i : issuesDb) {
            issuesDbService.saveOrUpdate(i);
            log.info(count = count - 1);
        }
    }

    private Set<CustomField> allCustomFieldsByIssues(Set<Issues> issuesJson) {
        Set<CustomField> customFields = new HashSet<>();
        for (var i : issuesJson) {
            if (!i.getCustomFields().isEmpty()) {
                for (var t : i.getCustomFields()) {
                    if (t.getValue() != null && !t.getValue().equals("")) {
                        customFields.add(new CustomField(0, t.getName(), t.getValue(), i.getId()));
                    }
                }
            }
        }
        return customFields;
    }

    private void saveAllCustomField(Set<Issues> issuesJson) {
        Set<CustomField> customFields = allCustomFieldsByIssues(issuesJson);
        int count = customFields.size();
        log.info("save custom fields redmine " + count);
        for (var f : customFields) {
            log.info((count = count - 1));
            Integer id = issuesDbService.issuesById(f.getIssuesId(), 0).getId();
            customFieldDbService.save(new CustomField(0, f.getName(), f.getValue(), id));
        }
    }
    private void saveAllTimeInterval(Set<TimeInterval> timeEntriesJsons) {
        int count = timeEntriesJsons.size();
        log.info("save time entries redmine" + count);
        LocalDate twoWeeks = LocalDate.now().minusDays(15);
        //todo Удаление записей за диапозон равный диапозону в методе timeIntervalsFromRedmineForToWeeks()
        timeEntriesDbService.deleteFromTo(twoWeeks, LocalDate.now());
        for (var t : timeEntriesJsons) {

            Integer projectId = t.getProject() == null ? null : projectDbService.projectByProjectIdAndSystemId(t.getProject().getId(), 0).getId();
            Integer issuesId = t.getIssue() == null ? null : issuesDbService.issuesById(t.getIssue().getId(), 0).getId();
            Integer userId = t.getUser() == null ? null : userDbService.userByUserIdAndSystemId(t.getUser().getId(), 0).getId();
            Integer activityId = t.getActivity() == null ? null : t.getActivity().getId();

            timeEntriesDbService.saveOrUpdate(new TimeEntriesDb(t.getId(), 0, projectId,
                    issuesId, userId, t.getHours(),
                    t.getComments(), t.getSpentOn(), t.getCreated(), t.getUpdated()));

            String s = t.getIssue() == null ? "" : t.getIssue().getId().toString();
            log.info((count = count - 1) + " entryes " + t.getId() + " issues " + s);
        }
    }
}