package soc.tech.bitrix24.web.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectBitrix {

    @JsonProperty("ID")
    private Integer id;
    @JsonProperty("NAME")
    private String name;

}
///socialnetwork.api.workgroup.get?params[groupId]=27