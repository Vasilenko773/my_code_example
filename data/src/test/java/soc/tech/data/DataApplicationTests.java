package soc.tech.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@SpringBootConfiguration
@TestPropertySource("classpath:application-test.properties")
public class DataApplicationTests {

    @Test
    void contextLoads() {
    }
}
