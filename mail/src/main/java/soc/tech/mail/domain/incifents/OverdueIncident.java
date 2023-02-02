package soc.tech.mail.domain.incifents;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class OverdueIncident {

    private final Integer id;
    private final String name;
    private final String project;
    private final String status;
    private final String priority;
    private final String executor;
    private final String startDate;
    private final String dueDate;
}
