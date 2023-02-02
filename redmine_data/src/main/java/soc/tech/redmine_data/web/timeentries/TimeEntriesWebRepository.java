package soc.tech.redmine_data.web.timeentries;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TimeEntriesWebRepository {

    private final TimeEntriesWebAdapter timeEntriesWebAdapter;

    public List<TimeInterval> timeIntervalsByProjectFromTo(int project, LocalDate from, LocalDate to) {
        return timeEntriesWebAdapter.timeIntervalsByProjectFromTo(project, from, to);
    }

    public List<TimeInterval> sortedBySpentOn(List<TimeInterval> timeIntervals) {
        return timeIntervals.stream()
                .sorted(Comparator.comparing(TimeInterval::getSpentOn))
                .collect(Collectors.toList());
    }

    public List<TimeInterval> sortedBySpentOnAndByUserName(List<TimeInterval> timeIntervals) {
        Comparator<TimeInterval> comparator = Comparator.comparing(t -> t.getUser().getName());
        return timeIntervals.stream()
                .sorted(comparator.thenComparing(TimeInterval::getSpentOn))
                .collect(Collectors.toList());
    }

    public JsonDataTimeEntries infoTimeEntries() {
        return timeEntriesWebAdapter.jsonDataTimeEntriesInfo();
    }

    public List<TimeInterval> timeIntervalsOffSet(int offset) {
        return timeEntriesWebAdapter.timeIntervalsByOffset(offset);
    }

    public JsonDataTimeEntries infoTimeEntriesForToday(LocalDate from, LocalDate to) {
        return timeEntriesWebAdapter.jsonDataTimeEntriesInfoForToday(from, to);
    }

    public List<TimeInterval> timeIntervalsFromToOffset(LocalDate from, LocalDate to, int offset) {
        return timeEntriesWebAdapter.timeIntervalsFromToByOffset(from, to, offset);
    }

}
