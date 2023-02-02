package soc.tech.bitrix24.web.timeentries;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TimeEntriesBitrixConfig {

    private @Value("${bitrix.time.entries.by.takid}") String timeEntriesByTaskId;
    private @Value("${bitrix.time.entries.by.date}") String timeEntriesByDay;
}
