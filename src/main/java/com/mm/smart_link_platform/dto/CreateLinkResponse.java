package com.mm.smart_link_platform.dto;

import lombok.Builder;

@Builder
public record CreateLinkResponse(

        String shortUrl
) {
}
