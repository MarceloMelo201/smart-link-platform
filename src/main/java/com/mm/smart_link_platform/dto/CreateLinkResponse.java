package com.mm.smart_link_platform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Response containing data from the short URL.")
@Builder
public record CreateLinkResponse(

        @Schema(description = "Shortened url", example = "WVgP8xqaG")
        String shortUrl
) {
}
