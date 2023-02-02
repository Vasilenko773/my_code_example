package soc.tech.bitrix24.web.issues.componensts.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import soc.tech.bitrix24.web.issues.IssueBitrix;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResultIssues {

    @JsonProperty("tasks")
    private List<IssueBitrix> tasks = new ArrayList<>();
}
