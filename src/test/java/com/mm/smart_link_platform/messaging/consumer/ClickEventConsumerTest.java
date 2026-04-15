package com.mm.smart_link_platform.messaging.consumer;

import com.mm.smart_link_platform.dto.ClickEvent;
import com.mm.smart_link_platform.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClickEventConsumerTest {

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private ClickEventConsumer consumer;

    @Test
    void shouldConsumeEventSuccessfully() {
        ClickEvent event = ClickEvent.builder()
                .linkId(UUID.randomUUID())
                .ip("127.0.0.1")
                .userAgent("Chrome")
                .referer("google.com")
                .build();

        consumer.consumeClickEvent(event);
        verify(analyticsService).registerAccess(event);
    }

    @Test
    void shouldHandleErrorAndNotThrowException() {
        ClickEvent event = ClickEvent.builder()
                .linkId(UUID.randomUUID())
                .build();

        doThrow(new RuntimeException())
                .when(analyticsService).registerAccess(any());

        assertDoesNotThrow(() -> consumer.consumeClickEvent(event));
        verify(analyticsService).registerAccess(event);
    }

}