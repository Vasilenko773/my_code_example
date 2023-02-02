package soc.tech.data.repository.db.timeentries;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeEntriesDbService {

    private final TimeEntriesDbRepository timeEntriesDbRepository;

    public void saveOrUpdate(TimeEntriesDb interval) {
        TimeEntriesDb entriesDb = timeEntriesDbRepository.intervalById(interval.getTimeEntriesId(), interval.getSystemId());
        if (entriesDb.getTimeEntriesId() == null) {
            timeEntriesDbRepository.save(interval);
        } else {
            timeEntriesDbRepository.update(interval);
        }
    }

    public List<TimeEntriesDb> allTimeEntriesFromRedmine() {
        return timeEntriesDbRepository.intervals();
    }

    public TimeEntriesDb timeEntryBySystemIdAndTimeEntriesId(int timeEntriesId, int systemId) {
        return timeEntriesDbRepository.intervalById(timeEntriesId, systemId);
    }

    public void deleteFromTo(LocalDate from, LocalDate to) {
        timeEntriesDbRepository.deleteFromToByRedmine(from, to);
    }

    public void deleteFromToBitrix(LocalDate from, LocalDate to) {
        timeEntriesDbRepository.deleteFromToByBitrix(from, to);
    }
}
