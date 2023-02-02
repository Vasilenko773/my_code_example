package soc.tech.redmine_data.web.timeentries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import soc.tech.redmine_data.web.issues.Issues;
import soc.tech.redmine_data.web.project.Project;
import soc.tech.redmine_data.web.timeentries.components.Activity;
import soc.tech.redmine_data.web.timeentries.components.User;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "time_entries")
public class TimeInterval {

    private Integer id;
    private Project project;
    @JsonProperty("issue")
    private Issues issue;
    private User user;
    private Activity activity;
    @JsonProperty("hours")
    private double hours;
    private String comments;
    @JsonProperty("spent_on")
    private LocalDate spentOn;
    @JsonProperty("created_on")
    private String created;
    @JsonProperty("updated_on")
    private String updated;

}