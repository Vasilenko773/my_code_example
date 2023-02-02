package soc.tech.tgbot.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class DrpoReportRgBotConfig {

    private @Value("${tg.bot.name}") String tgName;
    private @Value("${tg.bot.token}") String tgToken;
}
