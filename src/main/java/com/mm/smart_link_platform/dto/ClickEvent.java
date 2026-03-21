package com.mm.smart_link_platform.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ClickEvent(
        UUID linkId,
        String ip,
        String userAgent,
        String referer
) {
}
