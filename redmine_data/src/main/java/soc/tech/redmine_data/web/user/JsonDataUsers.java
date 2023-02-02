package soc.tech.redmine_data.web.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonDataUsers {

    @JsonProperty("users")
    private List<User> users = new ArrayList<>();
    @JsonProperty("total_count")
    private int count;
    @JsonProperty("offset")
    private int offset;
    @JsonProperty("limit")
    private int limit;
}
