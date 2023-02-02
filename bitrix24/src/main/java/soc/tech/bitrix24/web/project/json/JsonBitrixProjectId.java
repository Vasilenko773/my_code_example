package soc.tech.bitrix24.web.project.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBitrixProjectId {

    @JsonProperty("id")
    private Integer id;
}
