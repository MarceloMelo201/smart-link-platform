package com.mm.smart_link_platform.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CreateLinkRequest(

        @NotBlank(message = "The url cannot be blank.")
        String originalUrl
) {
}
