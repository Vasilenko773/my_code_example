package soc.tech.bitrix24.web.project.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import soc.tech.bitrix24.web.issues.componensts.json.JsonBitrixTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBitrixProjectDataId {


    @JsonProperty("result")
    private JsonBitrixListId result;
    private Integer next;
    private Integer total;
    @JsonProperty("time")
    private JsonBitrixTime time;
}
