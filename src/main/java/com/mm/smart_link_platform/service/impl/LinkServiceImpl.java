package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.dto.LinkResponse;
import com.mm.smart_link_platform.entity.Link;
import com.mm.smart_link_platform.exception.LinkExpiredException;
import com.mm.smart_link_platform.exception.LinkInactiveException;
import com.mm.smart_link_platform.exception.UrlNotFoundException;
import com.mm.smart_link_platform.repository.LinkRepository;
import com.mm.smart_link_platform.service.LinkService;
import com.mm.smart_link_platform.service.TokenService;
import com.mm.smart_link_platform.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class LinkServiceImpl implements LinkService {
    private static final int LINK_VALIDITY_DAYS = 3;

    private final LinkRepository repository;
    private final TokenService tokenService;
    private final ValidationService validationService;

    public LinkServiceImpl(LinkRepository repository, TokenService tokenService, ValidationService validationService) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    @Retryable(
            retryFor = DuplicateKeyException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 100))
    public CreateLinkResponse createShortLink(CreateLinkRequest request) {
        String url = request.originalUrl();
        url = validationService.validateUrl(url);
        String shortCode = tokenService.generateShortCode();

        Link link = Link
                .builder()
                .originalUrl(url)
                .shortCode(shortCode)
                .active(true)
                .expiresAt(LocalDateTime.now().plusDays(LINK_VALIDITY_DAYS))
                .build();

        repository.save(link);
        log.info("Saving to database entity link. | linkId={}", link.getLinkId());
        return new CreateLinkResponse(link.getShortCode());
    }

    @Override
    public LinkResponse findByShortCode(String shortCode) {
        Link link = repository.findByShortCode(shortCode)
                .orElseThrow(UrlNotFoundException::new);

        if (link.isExpired()) {
            log.error("Link is expired | linkId={}", link.getLinkId());
            throw new LinkExpiredException();
        }

        if (!link.isActive()) {
            log.error("Link is inactive | linkId={}", link.getLinkId());
            throw new LinkInactiveException();
        }

        return LinkResponse
                .builder()
                .linkId(link.getLinkId())
                .originalUrl(link.getOriginalUrl())
                .shortCode(link.getShortCode())
                .createdAt(link.getCreatedAt())
                .expiresAt(link.getExpiresAt())
                .build();
    }
}
