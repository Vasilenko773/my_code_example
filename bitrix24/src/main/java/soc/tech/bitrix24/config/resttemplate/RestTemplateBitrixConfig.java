package soc.tech.bitrix24.config.resttemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Log4j2
public class RestTemplateBitrixConfig {

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();

    }
}
