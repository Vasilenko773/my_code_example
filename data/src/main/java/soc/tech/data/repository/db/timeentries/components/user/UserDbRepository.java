package soc.tech.data.repository.db.timeentries.components.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDbRepository {

    private final JdbcTemplate jdbc;

    public UserDb userByUserIdAndSystemId(int userId, int systemId) {
        return jdbc.query("select id, userid, system_id, name from users where userid = ? and system_id = ?", (rs, row) -> {
            return new UserDb(rs.getInt("id"), rs.getInt("userid"),
                    rs.getInt("system_id"), rs.getString("name"));
        }, userId, systemId).stream().findFirst().orElse(new UserDb());
    }

    public UserDb userById(int id) {
        return jdbc.query("select id, userid, system_id, name from users where id = ?", (rs, row) -> {
            return new UserDb(rs.getInt("id"), rs.getInt("userid"),
                    rs.getInt("system_id"), rs.getString("name"));
        }, id).stream().findFirst().orElse(new UserDb());
    }

    public void save(UserDb user) {
        jdbc.update("insert into users(userid, system_id, name) values (?, ?, ?)",
               user.getUserId(), user.getSystemId(), user.getName());
    }

    public void update(UserDb user) {
        jdbc.update("update users set name = ? where userid = ? and system_id = ?",
                user.getName(), user.getUserId(), user.getSystemId());
    }

    public List<UserDb> users() {
        return jdbc.query("select * from users where users.userid IS NOT NULL",
                (rs, row) -> {
                    return new UserDb(rs.getInt("id"), rs.getInt("userid"),
                            rs.getInt("system_id"), rs.getString("name"));
                });
    }
}