package soc.tech.bitrix24.web.issues.componensts.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBitrixTime {
    private Double start;
    private Double finish;
    private Double duration;
    private Double processing;
    @JsonProperty("date_start")
    private String dateStart;
    @JsonProperty("date_finish")
    private String dateFinish;

}
