package com.mm.smart_link_platform.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LinkResponse(
        UUID linkId,
        String originalUrl,
        String shortCode,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {
}
