package soc.tech.redmine_data.web.issues.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTracking {

    private int id;
    private String name;
    private String value;
}
