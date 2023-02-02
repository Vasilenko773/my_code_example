package soc.tech.redmine_data.web.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "projects")
public class Project {

    private int id;
    private String name;
    private String identifier;
    private String description;
    @JsonProperty("parent")
    private Parent parent;
    private int status;
    @JsonProperty("is_public")
    private boolean isPublic;
    @JsonProperty("inherit_members")
    private boolean inheritMembers;
}
