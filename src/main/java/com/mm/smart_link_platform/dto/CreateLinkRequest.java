package com.mm.smart_link_platform.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLinkRequest(

        @NotBlank(message = "The url cannot be blank.")
        String originalUrl
) {
}
