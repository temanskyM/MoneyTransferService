package ru.netology.moneytransferservice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractContainerTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    public static GenericContainer<?> devApp = new GenericContainer<>("docker_money-tranfer-service");

    @BeforeAll
    public static void setUp() {
        devApp.start();
    }
}
