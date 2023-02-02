package soc.tech.redmine_data.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import soc.tech.redmine_data.web.project.JsonDataProjects;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserWebAdapter {

    private final RestTemplate restTemplate;
    private final UserConfig userConfig;

    public List<User> activeUsers(int offset) {
        ResponseEntity<JsonDataUsers> usersResponse =
                restTemplate.getForEntity(userConfig.getRedmine().concat(userConfig.getUsersActive()),
        JsonDataUsers.class, offset);
        return usersResponse.getBody().getUsers();
    }

    public List<User> closedUsers(int offset) {
        ResponseEntity<JsonDataUsers> usersResponse =
                restTemplate.getForEntity(userConfig.getRedmine().concat(userConfig.getUsersClosed()),
                        JsonDataUsers.class, offset);
        return usersResponse.getBody().getUsers();
    }

    public JsonDataUsers usersInfoActive() {
        ResponseEntity<JsonDataUsers> usersResponse =
                restTemplate.getForEntity(userConfig.getRedmine().concat(userConfig.getUsersActive()),
                        JsonDataUsers.class, 0);
        return usersResponse.getBody();
    }

    public JsonDataUsers usersInfoClosed() {
        ResponseEntity<JsonDataUsers> usersResponse =
                restTemplate.getForEntity(userConfig.getRedmine().concat(userConfig.getUsersClosed()),
                        JsonDataUsers.class, 0);
        return usersResponse.getBody();
    }
}
