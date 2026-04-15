package com.mm.smart_link_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response with access log data")
@Builder
public record AccessLogResponse(

        @Schema(description = "Unique access ID", example = "3d0ca315-aff9-4fc2-be61-3b76b9a2d798")
        UUID id,

        @Schema(description = "Unique Link ID", example = "3d0ca315-aff9-4fc2-be61-3b76b9a2d798")
        UUID LinkId,

        @Schema(description = "Access date", example = "2026-03-21 17:56:51.205951")
        LocalDateTime accessTime
) {
}
