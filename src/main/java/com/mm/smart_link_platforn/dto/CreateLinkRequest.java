package com.mm.smart_link_platforn.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateLinkRequest(

        @NotBlank(message = "The url cannot be blank.")
        String originalUrl
) {
}
