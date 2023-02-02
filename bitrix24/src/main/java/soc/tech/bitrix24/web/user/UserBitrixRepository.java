package soc.tech.bitrix24.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBitrixRepository {
    private final UserBitrixWebAdapter userBitrixWebAdapter;

    public JsonBitrixDataUsers usersInfo() {
        return userBitrixWebAdapter.usersInfo();
    }

    public List<UserBitrix> users(int count) {
        return userBitrixWebAdapter.allUsers(count);
    }
}
