package soc.tech.bitrix24.web.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBitrix {

    @JsonProperty("ID")
    private Integer id;
    @JsonProperty("ACTIVE")
    private Boolean active;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("LAST_NAME")
    private String lastNAme;
    @JsonProperty("SECOND_NAME")
    private String secondName;
    @JsonProperty("EMAIL")
    private String email;

}
