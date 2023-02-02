package soc.tech.redmine_data.web.timeentries;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class TimeEntriesConfig {

    private @Value("${redmine.url}") String redmine;
    private @Value("${timeEntries.url}") String timeEntries;
    private @Value("${info.timeEntries}") String timeEntriesInfo;
    private @Value("${offSet.timeEntries}") String timeEntriesOffSet;
    private @Value("${info.timeEntries.today}") String timeEntriesInfoForToday;
    private @Value("${offSet.timeEntries.today}") String timeEntriesOffSetToday;
}
