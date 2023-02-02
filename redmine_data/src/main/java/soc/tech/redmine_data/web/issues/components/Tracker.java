package soc.tech.redmine_data.web.issues.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "tracker")
public class Tracker {

    private int id;
    private String name;
}