package soc.tech.bitrix24.web.timeentries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEntriesBitrix {

    @JsonProperty("ID")
    private Integer id;
    @JsonProperty("TASK_ID")
    private Integer taskId;
    @JsonProperty("USER_ID")
    private Integer userId;
    @JsonProperty("COMMENT_TEXT")
    private String comment;
    @JsonProperty("SECONDS")
    private Integer seconds;
    @JsonProperty("MINUTES")
    private Integer minutes;
    @JsonProperty("SOURCE")
    private Integer source;
    @JsonProperty("CREATED_DATE")
    private String createDate;
    @JsonProperty("DATE_START")
    private String dateStart;
    @JsonProperty("DATE_STOP")
    private String dateStop;
}
