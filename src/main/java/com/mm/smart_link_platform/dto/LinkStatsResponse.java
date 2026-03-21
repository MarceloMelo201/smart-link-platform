package com.mm.smart_link_platform.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LinkStatsResponse(

        String originalUrl,
        String shortCode,
        Long totalClicks,
        Long uniqueIps,
        LocalDateTime createdAt,
        List<AccessLogResponse> recentAccesses

) {
}
