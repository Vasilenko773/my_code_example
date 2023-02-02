package soc.tech.redmine_data.web.issues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonDataIssueById {

    @JsonProperty("issue")
    private Optional<Issues> issue;
}
