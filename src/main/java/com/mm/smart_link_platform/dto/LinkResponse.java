package com.mm.smart_link_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response containing data from the Link.")
@Builder
public record LinkResponse(

        @Schema(description = "Unique link ID", example = "3d0ca315-aff9-4fc2-be61-3b76b9a2d798")
        UUID linkId,

        @Schema(description = "Original url", example = "https://google.com")
        String originalUrl,

        @Schema(description = "Shortened code", example = "WVgP8xqaG")
        String shortCode,

        @Schema(description = "Creation date", example = "2026-03-21 17:56:51.205951")
        LocalDateTime createdAt,

        @Schema(description = "Expiration date", example = "2026-03-21 17:56:51.205951")
        LocalDateTime expiresAt
) {
}
