package soc.tech.redmine_data.web.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class UserConfig {

    private @Value("${redmine.url}") String redmine;
    private @Value("${users.active.url}") String usersActive;
    private @Value("${users.closed.url}") String usersClosed;
}
