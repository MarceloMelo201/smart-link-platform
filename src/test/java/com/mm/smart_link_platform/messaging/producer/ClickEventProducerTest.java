package com.mm.smart_link_platform.messaging.producer;

import com.mm.smart_link_platform.dto.ClickEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClickEventProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ClickEventProducer producer;

    @Test
    void shouldPublishClickEventSuccessfully() {
        ClickEvent event = ClickEvent.builder()
                .linkId(UUID.randomUUID())
                .ip("127.0.0.1")
                .userAgent("Chrome")
                .referer("google.com")
                .build();

        producer.publishClickEvent(event);
        verify(rabbitTemplate).convertAndSend(
                eq("click-events-exchange"),
                eq("click-events-routing-key"),
                eq(event)
        );
    }

    @Test
    void shouldNotPublishNullEvent() {
        assertThrows(NullPointerException.class,
                () -> producer.publishClickEvent(null));
    }
}