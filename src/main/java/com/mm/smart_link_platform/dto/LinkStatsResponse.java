package com.mm.smart_link_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Response displaying link statistics data.")
@Builder
public record LinkStatsResponse(

        @Schema(description = "Original url", example = "https://google.com")
        String originalUrl,

        @Schema(description = "Shortened code", example = "WVgP8xqaG")
        String shortCode,

        @Schema(description = "Number of clicks", example = "20")
        Long totalClicks,

        @Schema(description = "Number of unique IPs", example = "30")
        Long uniqueIps,

        @Schema(description = "Creation date", example = "2026-03-21 17:56:51.205951")
        LocalDateTime createdAt,

        @Schema(description = "List of responses with access log data")
        List<AccessLogResponse> recentAccesses

) {
}
