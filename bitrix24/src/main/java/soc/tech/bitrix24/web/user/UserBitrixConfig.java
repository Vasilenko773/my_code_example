package soc.tech.bitrix24.web.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UserBitrixConfig {

    private @Value("${bitrix.allUsers.offset}") String users;
}
