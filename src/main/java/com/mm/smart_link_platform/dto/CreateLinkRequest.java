package com.mm.smart_link_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request containing data from the original URL.")
public record CreateLinkRequest(

        @Schema(description = "Original url", example = "https://google.com")
        @NotBlank(message = "The url cannot be blank.")
        String originalUrl
) {
}
