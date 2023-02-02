package soc.tech.bitrix24.web.timeentries;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import soc.tech.bitrix24.config.bitrix.BitrixConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TimeEntriesBitrixWebAdapter {

    private final RestTemplate restTemplate;
    private final BitrixConfig bitrixConfig;
    private final TimeEntriesBitrixConfig timeEntriesBitrixConfig;

    public List<TimeEntriesBitrix> timeEntriesByPageId(int id) {
        ResponseEntity<JsonBitrixTimeEntriesData> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(timeEntriesBitrixConfig.getTimeEntriesByTaskId()),
                        JsonBitrixTimeEntriesData.class, id);
        return issuesResponse.getBody().getResult();

    }

    public JsonBitrixTimeEntriesData infoTimeEntries() {
        ResponseEntity<JsonBitrixTimeEntriesData> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(timeEntriesBitrixConfig.getTimeEntriesByTaskId()),
                        JsonBitrixTimeEntriesData.class, 0);
        return issuesResponse.getBody();
    }


    public JsonBitrixTimeEntriesData infoTimeEntriesByDate(LocalDate date, int count)  {
        Map<String, Object> keys = Map.of("date", date, "count", count);
        ResponseEntity<JsonBitrixTimeEntriesData> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(timeEntriesBitrixConfig.getTimeEntriesByDay()),
                        JsonBitrixTimeEntriesData.class, keys);
        return issuesResponse.getBody();
    }

    public List<TimeEntriesBitrix> timeEntriesByDateAndPageId(LocalDate date, int count) {
        Map<String, Object> keys = Map.of("date", date, "count", count);
        ResponseEntity<JsonBitrixTimeEntriesData> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(timeEntriesBitrixConfig.getTimeEntriesByDay()),
                        JsonBitrixTimeEntriesData.class, keys);
        return issuesResponse.getBody().getResult();
    }
}
