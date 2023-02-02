package soc.tech.mail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@SpringBootConfiguration
@TestPropertySource("classpath:application-test.properties")
class MailApplicationTests {

    @Test
    void contextLoads() {
    }

}
