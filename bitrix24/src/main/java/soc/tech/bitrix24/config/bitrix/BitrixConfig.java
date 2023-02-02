package soc.tech.bitrix24.config.bitrix;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BitrixConfig {

    private @Value("${bitrix.url}") String bitrix;
}
