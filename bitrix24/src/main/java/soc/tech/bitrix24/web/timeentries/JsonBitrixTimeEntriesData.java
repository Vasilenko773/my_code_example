package soc.tech.bitrix24.web.timeentries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import soc.tech.bitrix24.web.issues.componensts.json.JsonBitrixTime;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBitrixTimeEntriesData {

    @JsonProperty("result")
    private List<TimeEntriesBitrix> result = new ArrayList<>();
    private Integer next;
    private Integer total;
    @JsonProperty("time")
    private JsonBitrixTime time;
}

