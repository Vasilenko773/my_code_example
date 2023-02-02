package soc.tech.data.domain.dataloader.bitrix;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import soc.tech.bitrix24.web.issues.IssueBitrix;
import soc.tech.bitrix24.web.issues.IssueBitrixRepository;
import soc.tech.bitrix24.web.project.ProjectBitrix;
import soc.tech.bitrix24.web.project.ProjectBitrixRepository;
import soc.tech.bitrix24.web.timeentries.TimeEntriesBitrix;
import soc.tech.bitrix24.web.timeentries.TimeEntriesBitrixRepository;
import soc.tech.bitrix24.web.user.UserBitrix;
import soc.tech.bitrix24.web.user.UserBitrixRepository;
import soc.tech.data.repository.db.issues.IssuesDb;
import soc.tech.data.repository.db.issues.IssuesDbService;
import soc.tech.data.repository.db.timeentries.TimeEntriesDb;
import soc.tech.data.repository.db.timeentries.TimeEntriesDbService;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDb;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDbService;
import soc.tech.data.repository.db.timeentries.components.user.UserDb;
import soc.tech.data.repository.db.timeentries.components.user.UserDbService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class BitrixDataLoaderService {

    private final IssueBitrixRepository issueBitrixRepository;
    private final UserBitrixRepository userBitrixRepository;
    private final TimeEntriesBitrixRepository timeEntriesBitrixRepository;
    private final ProjectBitrixRepository projectBitrixRepository;
    private final UserDbService userDbService;
    private final ProjectDbService projectDbService;
    private final IssuesDbService issuesDbService;
    private final TimeEntriesDbService timeEntriesDbService;

    @Scheduled(cron = "${data.all.cron}")
    @Async
    public void saveAllElement() {
        saveAllUserDbFromBitrix();
        saveAllProjectFromBitrix();
        Set<IssueBitrix> issueBitrixes = allIssuesByBitrix();
        saveAllIssuesFromBitrix(issueBitrixes);
        List<TimeEntriesBitrix> timeEntries = allTimeEntries();
        saveAllTimeIntervalFromBitrix(timeEntries);
    }

    @Scheduled(cron = "${data.cron.bitrix}")
    @Async
    public void saveTwoWeeksElement() {
        saveAllUserDbFromBitrix();
        saveAllProjectFromBitrix();
        LocalDate twoWeek = LocalDate.now().minusDays(15);
        Set<IssueBitrix> issueBitrixes = allIssuesByBitrixByDate(twoWeek);
        saveAllIssuesFromBitrix(issueBitrixes);
        List<TimeEntriesBitrix> timeEntries = timeEntriesByDate(twoWeek);
        //todo Удаление записей за диапозон равный диапозону в методе timeIntervalsFromRedmineForToWeeks()
        timeEntriesDbService.deleteFromToBitrix(twoWeek, LocalDate.now());
        saveAllTimeIntervalFromBitrix(timeEntries);
    }

    private void saveAllUserDbFromBitrix() {
        Set<UserBitrix> userJsons = allUsersFromBitrix();
        int count = userJsons.size();
        log.info("user count for bitrix: " + count);
        for (var u : userJsons) {
            String name = u.getName() + " " + u.getLastNAme();
            userDbService.saveOrUpdate(new UserDb(u.getId(), 1, name));
            log.info(count = count - 1);
        }
    }

    private Set<UserBitrix> allUsersFromBitrix() {
        Set<UserBitrix> usersJson = new HashSet<>();
        int count = userBitrixRepository.usersInfo().getTotal();
        int size = 0;
        while (size <= count) {
            List<UserBitrix> users = userBitrixRepository.users(size);
            usersJson.addAll(users);
            size += 50;
        }
        return usersJson;
    }

    private void saveAllProjectFromBitrix() {
        Set<ProjectBitrix> projects = allProjects();
        int count = projects.size();
        log.info("project count for bitrix: " + count);
        for (var p : projects) {
            ProjectDb project = new ProjectDb(p.getId(), 1, p.getName());
            projectDbService.saveOrUpdate(project);
            log.info(count = count - 1);
        }
    }

    private Set<ProjectBitrix> allProjects() {
        List<ProjectBitrix> projects = projectBitrixRepository.projects();
        return new HashSet<>(projects);
    }

    private void saveAllIssuesFromBitrix(Set<IssueBitrix> allIssuesByBitrix) {
        int count = allIssuesByBitrix.size();
        log.info("issues count for bitrix: " + count);
        for (var i : allIssuesByBitrix) {
            Double hours = i.getTimeSpentInLogs() == null ? null : i.getTimeSpentInLogs() / 60;
            Integer userId = userDbService.userByUserIdAndSystemId(i.getResponsibleId(), 1).getId();
            Integer projectId = projectDbService.projectByProjectIdAndSystemId(i.getGroupId(), 1).getId();
            IssuesDb issue = new IssuesDb(i.getId(), 1, projectId, "Задача", status(i.getStatus()), priority(i.getPriority()),
                    userId, i.getTitle(), i.getDescription(), date(i.getDateStart()), date(i.getDeadLine()),
                    0, hours, new ArrayList<>(), i.getCreatedDate(), i.getChangedDate(), i.getClosedDate());
            issuesDbService.saveOrUpdate(issue);
            log.info("issues save " + (count = count - 1));
        }
    }

    private Set<IssueBitrix> allIssuesByBitrix() {
        Set<IssueBitrix> issuesJsons = new HashSet<>();
        int count = issueBitrixRepository.issuesInfo().getTotal();
        int size = 0;
        while (size <= count) {
            List<IssueBitrix> issues = issueBitrixRepository.allIssue(size);
            issuesJsons.addAll(issues);
            size += 50;
        }
        return issuesJsons;
    }

    private Set<IssueBitrix> allIssuesByBitrixByDate(LocalDate date) {
        Set<IssueBitrix> issuesJsons = new HashSet<>();
        int count = issueBitrixRepository.issuesInfoByDate(date, 0).getTotal();
        int size = 0;
        while (size <= count) {
            List<IssueBitrix> issues = issueBitrixRepository.allIssuesByDate(date, size);
            issuesJsons.addAll(issues);
            size += 50;
        }
        return issuesJsons;
    }


    private String status(int i) {
        switch (i) {
            case 1:
                return "Открыто";
            case 2:
                return "В очереди";
            case 3:
                return "В работе";
            case 4:
                return "Выполнено";
            case 5:
                return "Закрыто";
            case 6:
                return "Ожидание";
            case 7:
                return "Отклонено";
        }
        return "";
    }

    private String priority(int i) {
        switch (i) {
            case 0:
                return "Низкий";
            case 1:
                return "Нормальный";
            case 2:
                return "Высокий";
        }
        return "";
    }

    private LocalDate date(String stringDate) {
        stringDate = stringDate != null ? stringDate.split("T")[0] : null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return stringDate == null ? null : LocalDate.parse(stringDate, formatter);
    }

    private void saveAllTimeIntervalFromBitrix(List<TimeEntriesBitrix> allTimeEntries) {
        int count = allTimeEntries.size();
        log.info("time entries count for bitrix: " + count);
        for (var t : allTimeEntries) {
            if (t.getTaskId() != 1099 && t.getTaskId() != 1109 && t.getTaskId() != 1115 && t.getTaskId() != 1108) {
                log.info(t.getTaskId() + " issue id");
                log.info(t.getId() + " time id");
                IssuesDb issuesDb = issuesDbService.issuesById(t.getTaskId(), 1);
                Integer userId = userDbService.userByUserIdAndSystemId(t.getUserId(), 1).getId();
                Integer projectId = projectDbService.projectById(issuesDb.getProjectId()).getId();

                double hours = BigDecimal.valueOf((double) t.getMinutes() / 60).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                timeEntriesDbService.saveOrUpdate(new TimeEntriesDb(t.getId(), 1, projectId,
                        issuesDb.getId(), userId, hours,
                        t.getComment(), date(t.getCreateDate()), t.getDateStart(), null));
                log.info((count = count - 1) + " " + t.getId());
            }
        }
    }

    private List<TimeEntriesBitrix> allTimeEntries() {
        List<TimeEntriesBitrix> timeEntriesJson = new ArrayList<>();
        Integer total = timeEntriesBitrixRepository.infoTimeEntries().getTotal();
        double count = (double) total / 50;
        int i = 1;
        while (count > 0) {
            count -= 1;
            List<TimeEntriesBitrix> timeEntries = timeEntriesBitrixRepository.timeEntriesByPageId(i++);
            timeEntriesJson.addAll(timeEntries);
        }
        return timeEntriesJson;
    }

    private List<TimeEntriesBitrix> timeEntriesByDate(LocalDate date) {
        List<TimeEntriesBitrix> timeEntriesJson = new ArrayList<>();
        Integer total = timeEntriesBitrixRepository.infoTimeEntriesByDate(date, 0).getTotal();
        double count = (double) total / 50;
        int i = 1;
        while (count > 0) {
            count -= 1;
            List<TimeEntriesBitrix> timeEntries = timeEntriesBitrixRepository.timeEntriesByDateAndPageId(date, i++);
            timeEntriesJson.addAll(timeEntries);
        }
        return timeEntriesJson;
    }

}