package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.dto.ClickEvent;
import com.mm.smart_link_platform.dto.LinkResponse;
import com.mm.smart_link_platform.exception.LinkExpiredException;
import com.mm.smart_link_platform.messaging.producer.ClickEventProducer;
import com.mm.smart_link_platform.service.LinkService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedirectServiceImplTest {

    @Mock
    private LinkService linkService;

    @Mock
    private ClickEventProducer producer;

    @InjectMocks
    private RedirectServiceImpl redirectService;

    @Nested
    class ResolveLink {

        @Test
        void shouldResolveLinkSuccessfully() {
            String shortCode = "XAgTj27Yk";
            String ip = "127.0.0.1";
            String userAgent = "Mozilla";
            String referer = "google.com";

            LinkResponse response = LinkResponse
                    .builder()
                    .linkId(UUID.randomUUID())
                    .originalUrl("https://google.com")
                    .shortCode(shortCode)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusDays(3))
                    .build();

            when(linkService.findActiveLinkByShortCode(shortCode))
                    .thenReturn(response);

            String result = redirectService.resolveLink(shortCode, ip, userAgent, referer);
            assertEquals("https://google.com", result);

            verify(linkService).findActiveLinkByShortCode(shortCode);

            ArgumentCaptor<ClickEvent> captor = ArgumentCaptor.forClass(ClickEvent.class);
            verify(producer).publishClickEvent(captor.capture());

            ClickEvent event = captor.getValue();

            assertEquals(response.linkId(), event.linkId());
            assertEquals(ip, event.ip());
            assertEquals(userAgent, event.userAgent());
            assertEquals(referer, event.referer());
        }

        @Test
        void shouldThrowExceptionAndNotSendEventWhenLinkIsExpired() {
            String shortCode = "XAgTj27Yk";

            when(linkService.findActiveLinkByShortCode(shortCode))
                    .thenThrow(new LinkExpiredException());

            assertThrows(LinkExpiredException.class,
                    () -> redirectService.resolveLink(shortCode, "127.0.0.1", "Mozilla", "google.com"));

            verify(producer, never()).publishClickEvent(any());
        }
    }
}