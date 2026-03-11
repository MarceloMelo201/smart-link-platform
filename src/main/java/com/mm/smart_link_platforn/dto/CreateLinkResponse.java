package com.mm.smart_link_platforn.dto;

import lombok.Builder;

@Builder
public record CreateLinkResponse(

        String shortUrl,
        String qrCodeUrl
) {
}
