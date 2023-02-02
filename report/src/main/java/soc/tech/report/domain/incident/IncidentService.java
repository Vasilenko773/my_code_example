package soc.tech.report.domain.incident;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDbRepository;
import soc.tech.data.repository.db.timeentries.components.user.UserDbRepository;
import soc.tech.report.util.DateFormatBuilder;
import soc.tech.report.util.ReportDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class IncidentService {

    private final IncidentsRepository incidentsRepository;
    private final UserDbRepository userDbRepository;
    private final ProjectDbRepository projectDbRepository;
    private final DateFormatBuilder dateFormatBuilder;

    public List<Incident> newIncidentsDb() {
        return incidents().stream().filter(i -> i.getStatus().equals("Открыто")).collect(Collectors.toList());
    }

    public List<Incident> overdueDb() {
        return incidents().stream()
                .filter(i -> i.getDueDate() != null)
                .filter(i -> LocalDate.parse(i.getDueDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")).isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    private List<Incident> incidents() {
        List<Incident> incidents = new ArrayList<>();
        List<IncidentByIssue> incidentByIssues = incidentsRepository.incidentsByIssues();
        if (!incidentByIssues.isEmpty()) {
            for (var byIssues : incidentByIssues) {
                //todo передается 0 в метод userById так как пока что инциденты на изменение есть только в Redmine
                String user = byIssues.getUserId() == null ? "" : userDbRepository.userById(byIssues.getUserId()).getName();
                //todo передается 0 в метод projectById так как пока что инциденты на изменение есть только в Redmine
                String project = projectDbRepository.projectById(byIssues.getProjectId()).getName();
                incidents.add(new Incident(byIssues.getId(), byIssues.getName(), project, byIssues.getStatus(), byIssues.getPriority(),
                        user, dateFormatBuilder.dateByFormat(new ReportDate(byIssues.getStartDate(), "dd.MM.yyyy")),
                       byIssues.getDueDate() == null ? null : dateFormatBuilder.dateByFormat(new ReportDate(byIssues.getDueDate(), "dd.MM.yyyy"))));
            }
        }
        return incidents;
    }
}
