package com.mm.smart_link_platform.config;

import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RabbitTestConfig {

    @Bean
    public AmqpTemplate amqpTemplate() {
        return Mockito.mock(AmqpTemplate.class);
    }
}
