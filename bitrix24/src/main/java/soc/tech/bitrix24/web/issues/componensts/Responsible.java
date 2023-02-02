package soc.tech.bitrix24.web.issues.componensts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Responsible {

    private Integer id;
    private String name;

}
