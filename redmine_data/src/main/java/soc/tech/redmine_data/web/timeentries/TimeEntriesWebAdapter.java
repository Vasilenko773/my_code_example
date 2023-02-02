package soc.tech.redmine_data.web.timeentries;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TimeEntriesWebAdapter {

    private final RestTemplate restTemplate;
    private final TimeEntriesConfig timeEntriesConfig;

    public List<TimeInterval> timeIntervalsByProjectFromTo(int project, LocalDate from, LocalDate to) {
        Map<String, Object> keys = Map.of("project", project, "from", from, "to", to);
        ResponseEntity<JsonDataTimeEntries> timeEntriesResponse =
                restTemplate.getForEntity(timeEntriesConfig.getRedmine().concat(timeEntriesConfig.getTimeEntries()),
                        JsonDataTimeEntries.class, keys);
        return timeEntriesResponse.getBody().getTimeIntervals();
    }
    public JsonDataTimeEntries jsonDataTimeEntriesInfo() {
        ResponseEntity<JsonDataTimeEntries> timeEntriesResponse =
                restTemplate.getForEntity(timeEntriesConfig.getRedmine().concat(timeEntriesConfig.getTimeEntriesInfo()),
                        JsonDataTimeEntries.class);
        return timeEntriesResponse.getBody();
    }

    public JsonDataTimeEntries jsonDataTimeEntriesInfoForToday(LocalDate from, LocalDate to) {
        Map<String, Object> keys = Map.of("from", from, "to", to);
        ResponseEntity<JsonDataTimeEntries> timeEntriesResponse =
                restTemplate.getForEntity(timeEntriesConfig.getRedmine().concat(timeEntriesConfig.getTimeEntriesInfoForToday()),
                        JsonDataTimeEntries.class, keys);
        return timeEntriesResponse.getBody();
    }

    public List<TimeInterval> timeIntervalsByOffset(int offset) {
        ResponseEntity<JsonDataTimeEntries> timeEntriesResponse =
                restTemplate.getForEntity(timeEntriesConfig.getRedmine().concat(timeEntriesConfig.getTimeEntriesOffSet()),
                        JsonDataTimeEntries.class, offset);
        return timeEntriesResponse.getBody().getTimeIntervals();
    }

    public List<TimeInterval> timeIntervalsFromToByOffset(LocalDate from, LocalDate to, int offset) {

        Map<String, Object> keys = Map.of("from", from, "to", to, "offset", offset);
        ResponseEntity<JsonDataTimeEntries> timeEntriesResponse =
                restTemplate.getForEntity(timeEntriesConfig.getRedmine() + timeEntriesConfig.getTimeEntriesOffSetToday(),
                        JsonDataTimeEntries.class, keys);
        return timeEntriesResponse.getBody().getTimeIntervals();
    }
}
