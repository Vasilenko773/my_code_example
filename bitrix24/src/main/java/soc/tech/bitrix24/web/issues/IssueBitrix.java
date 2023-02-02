package soc.tech.bitrix24.web.issues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import soc.tech.bitrix24.web.issues.componensts.Responsible;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "tasks")
public class IssueBitrix {

    private Integer id;
    private Integer parentId;
    private String title;
    private String description;
    private Integer priority;
    private Integer status;
    @JsonProperty("groupId")
    private Integer groupId;
    private Integer createdBy;
    private String createdDate;
    private Integer responsibleId;
    private Integer closedBy;
    private String closedDate;
    private String dateStart;
    private String deadLine;
    @JsonProperty("changedDate")
    private String changedDate;
    @JsonProperty("timeEstimate")
    private Double timeEstimate;
    @JsonProperty("timeSpentInLogs")
    private Double timeSpentInLogs;
    @JsonProperty("responsible")
    private Responsible responsible;
}



