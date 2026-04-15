package com.mm.smart_link_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Schema(description = "DTO that transports click event data.")
@Builder
public record ClickEvent(

        @Schema(description = "Unique link ID", example = "3d0ca315-aff9-4fc2-be61-3b76b9a2d798")
        UUID linkId,

        @Schema(description = "Client ip", example = "200.148.12.188")
        String ip,

        @Schema(description = "Browser/system identification", example = "PostmanRuntime/7.52.0")
        String userAgent,

        @Schema(description = "The address of the previous web page that originated the current request.", example = "https://google.com")
        String referer
) {
}
