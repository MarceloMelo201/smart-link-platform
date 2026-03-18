package com.mm.smart_link_platform.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LinkStatsResponse(

        String originalUrl,
        String shortUrl,
        Long totalClicks,
        Long uniqueIps,
        LocalDateTime createdAt

) {
}
