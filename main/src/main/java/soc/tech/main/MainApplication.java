package soc.tech.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"soc.tech.data", "soc.tech.mail", "soc.tech.tgbot",
        "soc.tech.bitrix24", "soc.tech.redmine_data", "soc.tech.report"})
@PropertySource(value = {"classpath:application.properties", "classpath:application-path.properties",
        "classpath:application-mail.properties", "classpath:application-bot.properties",
        "classpath:application-bitrix24.properties", "classpath:application-redmine.properties",
        "classpath:application-report.properties"})
public class MainApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
