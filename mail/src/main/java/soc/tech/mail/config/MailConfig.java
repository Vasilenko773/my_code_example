package soc.tech.mail.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Getter
public class MailConfig {

    private @Value("${mail.host}") String host;
    private @Value("${mail.port}") Integer port;
    private @Value("${mail.username}") String username;
    private @Value("${mail.password}") String password;
    private @Value("${mail.username.to1}") String usernameTo1;
    private @Value("${mail.username.to2}") String usernameTo2;
    private @Value("${mail.username.to3}") String usernameTo3;
    private @Value("${mail.username.to4}") String usernameTo4;
    private @Value("${mail.cron}") String cron;

    @Bean
    public JavaMailSender JavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
