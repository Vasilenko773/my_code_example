package soc.tech.mail.domain.task14days;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Tasks14Days {

    private final int id;
    private final String name;
    private final String status;
    private final String executor;
    private final String priority;
    private final String startDate;
    private final String deadline;
}
