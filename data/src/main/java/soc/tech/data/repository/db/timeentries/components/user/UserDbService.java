package soc.tech.data.repository.db.timeentries.components.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDbService {

    private final UserDbRepository userDbRepository;

    public void saveOrUpdate(UserDb user) {
            UserDb userDb = userDbRepository.userByUserIdAndSystemId(user.getUserId(), user.getSystemId());
            if (userDb.getId() == null) {
                userDbRepository.save(user);
            } else {
                userDbRepository.update(user);
            }
        }

        public UserDb userById(int id) {
        return userDbRepository.userById(id);
        }

    public UserDb userByUserIdAndSystemId(int userId, int systemId) {
        return userDbRepository.userByUserIdAndSystemId(userId, systemId);
    }
}


