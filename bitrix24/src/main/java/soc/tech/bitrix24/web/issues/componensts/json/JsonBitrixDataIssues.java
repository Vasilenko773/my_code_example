package soc.tech.bitrix24.web.issues.componensts.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBitrixDataIssues {

    @JsonProperty("result")
    private JsonResultIssues result;
    private Integer next;
    private Integer total;
    @JsonProperty("time")
    private JsonBitrixTime time;
}
