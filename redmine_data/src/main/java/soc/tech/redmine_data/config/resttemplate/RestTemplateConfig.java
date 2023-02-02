package soc.tech.redmine_data.config.resttemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

@Configuration
@Log4j2
public class RestTemplateConfig {

    private @Value("${user.name}") String username;
    private @Value("${user.password}") String password;
    @Bean
    @DependsOn(value = {"restTemplateBuilder"})
    public RestTemplate getTemplate(RestTemplateBuilder restTemplateBuilder) {
        log.info("Инициализация @Bean Resttemplate");
        System.out.println("User name: ");
        log.info(username);
        System.out.println("Password: ");
        log.info(password);
        return restTemplateBuilder.basicAuthentication(username, password).build();
    }
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
