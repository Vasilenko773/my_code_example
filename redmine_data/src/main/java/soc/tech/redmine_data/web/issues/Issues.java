package soc.tech.redmine_data.web.issues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import soc.tech.redmine_data.web.issues.components.*;
import soc.tech.redmine_data.web.project.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "issues")
public class Issues {

    private Integer id;
    private Project project;
    private Tracker tracker;
    private Status status;
    private Priority priority;
    private Author author;
    @JsonProperty("assigned_to")
    private Assigned assigned;
    private String subject;
    private String description;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("due_date")
    private LocalDate dueDate;
    @JsonProperty("done_ratio")
    private Integer doneRatio;
    @JsonProperty("is_private")
    private boolean isPrivate;
    @JsonProperty("estimated_hours")
    private double estimatedHours;
    @JsonProperty("custom_fields")
    List<TimeTracking> customFields = new ArrayList<>();
    @JsonProperty("created_on")
    private String created;
    @JsonProperty("updated_on")
    private String updated;
    @JsonProperty("closed_on")
    private String closed;

}

