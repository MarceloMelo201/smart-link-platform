package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.entity.Link;
import com.mm.smart_link_platform.exception.LinkExpiredException;
import com.mm.smart_link_platform.exception.LinkInactiveException;
import com.mm.smart_link_platform.exception.UrlNotFoundException;
import com.mm.smart_link_platform.repository.LinkRepository;
import com.mm.smart_link_platform.service.TokenService;
import com.mm.smart_link_platform.service.ValidationService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @Mock
    private LinkRepository repository;

    @Mock
    private TokenService tokenService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private LinkServiceImpl linkService;


    @Nested
    class CreateShortLink {

        @Test
        void shouldCreateShortLinkSuccessfully() {
            CreateLinkRequest request = new CreateLinkRequest("https://google.com");
            when(validationService.validateUrl(any())).thenReturn("https://google.com");
            when(tokenService.generateShortCode()).thenReturn("XAgTj27Yk");

            CreateLinkResponse response = linkService.createShortLink(request);
            assertNotNull(response);
            assertEquals("XAgTj27Yk", response.shortUrl());
            verify(repository).save(any(Link.class));
        }
    }

    @Nested
    class FindActiveLinkByShortCode {

        @Test
        void shouldThrowExceptionWhenLinkIsNotFound() {
            when(repository.findByShortCode("XAgTj27Yk"))
                    .thenReturn(Optional.empty());

            assertThrows(UrlNotFoundException.class,
                    () -> linkService.findActiveLinkByShortCode("XAgTj27Yk"));
        }

        @Test
        void shouldThrowExceptionWhenLinkIsInactive() {
            Link link = Link
                    .builder()
                    .originalUrl("https://google.com")
                    .shortCode("zAgTt37Yx")
                    .active(false)
                    .expiresAt(LocalDateTime.now().plusDays(3))
                    .build();

            when(repository.findByShortCode("zAgTt37Yx"))
                    .thenReturn(Optional.of(link));

            assertThrows(LinkInactiveException.class,
                    () -> linkService.findActiveLinkByShortCode("zAgTt37Yx"));
        }

        @Test
        void shouldThrowExceptionWhenLinkIsExpired() {
            Link link = Link
                    .builder()
                    .originalUrl("https://google.com")
                    .shortCode("zAgTt37Yx")
                    .active(true)
                    .expiresAt(LocalDateTime.now().minusDays(1))
                    .build();

            when(repository.findByShortCode("zAgTt37Yx"))
                    .thenReturn(Optional.of(link));

            assertThrows(LinkExpiredException.class,
                    () -> linkService.findActiveLinkByShortCode("zAgTt37Yx"));

            verify(repository).save(any(Link.class));
        }


    }
}