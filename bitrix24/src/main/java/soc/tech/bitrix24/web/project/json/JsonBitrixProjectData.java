package soc.tech.bitrix24.web.project.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import soc.tech.bitrix24.web.issues.componensts.json.JsonBitrixTime;
import soc.tech.bitrix24.web.project.ProjectBitrix;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBitrixProjectData {

    @JsonProperty("result")
    private List<ProjectBitrix> result = new ArrayList<>();
    private Integer next;
    private Integer total;
    @JsonProperty("time")
    private JsonBitrixTime time;
}
