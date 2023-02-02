package soc.tech.bitrix24.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import soc.tech.bitrix24.config.bitrix.BitrixConfig;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBitrixWebAdapter {

    private final RestTemplate restTemplate;
    private final BitrixConfig bitrixConfig;
    private final UserBitrixConfig userBitrixConfig;


    public JsonBitrixDataUsers usersInfo() {
        ResponseEntity<JsonBitrixDataUsers> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(userBitrixConfig.getUsers()),
        JsonBitrixDataUsers.class, 0);
        return issuesResponse.getBody();
    }

    public List<UserBitrix> allUsers(int count) {
        ResponseEntity<JsonBitrixDataUsers> issuesResponse =
                restTemplate.getForEntity(bitrixConfig.getBitrix().concat(userBitrixConfig.getUsers()),
                        JsonBitrixDataUsers.class, count);
        return issuesResponse.getBody().getResult();
    }
}
