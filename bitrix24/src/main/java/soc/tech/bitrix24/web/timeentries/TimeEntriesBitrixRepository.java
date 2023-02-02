package soc.tech.bitrix24.web.timeentries;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeEntriesBitrixRepository {

    private final TimeEntriesBitrixWebAdapter timeEntriesBitrixWebAdapter;

    public List<TimeEntriesBitrix> timeEntriesByPageId(int id) {
        return timeEntriesBitrixWebAdapter.timeEntriesByPageId(id);
    }

    public JsonBitrixTimeEntriesData infoTimeEntries() {
        return timeEntriesBitrixWebAdapter.infoTimeEntries();
    }

    public JsonBitrixTimeEntriesData infoTimeEntriesByDate(LocalDate date, int count) {
        return timeEntriesBitrixWebAdapter.infoTimeEntriesByDate(date, count);
    }

    public List<TimeEntriesBitrix> timeEntriesByDateAndPageId(LocalDate date, int count) {
        return timeEntriesBitrixWebAdapter.timeEntriesByDateAndPageId(date, count);
    }

}
