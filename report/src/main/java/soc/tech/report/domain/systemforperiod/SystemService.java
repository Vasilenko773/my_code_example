package soc.tech.report.domain.systemforperiod;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDb;
import soc.tech.data.repository.db.timeentries.components.project.ProjectDbRepository;
import soc.tech.report.domain.systemforperiod.day.Day;
import soc.tech.report.domain.systemforperiod.day.DayService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemService {
    private final DayService dayService;
    private final ProjectDbRepository projectDbRepository;

    public List<System> systemsFromDb(LocalDate from, LocalDate to) {
        from = from == null ? startDateMonday() : from;
        to = to == null ? LocalDate.now().plusDays(1) : to;

        List<System> systems = new ArrayList<>();
        List<ProjectDb> projects = projectDbRepository.projects();
        int i = 0;
        for (ProjectDb project : projects) {
            List<Day> days = dayService.daysByProjectFromToSortByDateForDb(project.getId(), from, to);
            double sum = days.stream().mapToDouble(Day::getHours).sum();
            if (sum > 0) {
                double hours = new BigDecimal(sum).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                systems.add(new System(++i, project.getName(), hours, days));
            }
        }
        return systems;
    }

    public LocalDate startDateMonday() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}

