package soc.tech.bitrix24.web.project.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonBitrixListId {

    @JsonProperty("workgroups")
    private List<JsonBitrixProjectId> workgroups = new ArrayList<>();
}
