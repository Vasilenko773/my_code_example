package soc.tech.tgbot.domain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatTgRepository {

    private final JdbcTemplate jdbc;

    public Chat chatById(Long id) {
        return jdbc.query("select id from chats_tg where id = ?", (rs, row) -> {
            return new Chat(rs.getLong("id"), new ArrayList<>());
        }, id).stream().findFirst().orElse(new Chat());
    }

    public void save(Chat chat) {
        jdbc.update("insert into chats_tg(id)" +
                        " values (?)",
                chat.getId());
    }

    public List<Chat> chats() {
        return jdbc.query("select id from chats_tg", (rs, row) -> {
            return new Chat(rs.getLong("id"), new ArrayList<>());
        });
    }

    public void deleteChat(Long id) {
        jdbc.update("delete from chats_employees_tg where chat_id = (?)",
                id);
        jdbc.update("delete from chats_tg where id = (?)",
                id);
    }

    public void deleteUser(Integer UserId, Long ChatId) {
        jdbc.update("delete from chats_employees_tg where employee_id = (?) and chat_id = (?)",
                UserId, ChatId);
    }
}
