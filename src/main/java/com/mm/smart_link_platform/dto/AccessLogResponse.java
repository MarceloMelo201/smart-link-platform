package com.mm.smart_link_platform.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AccessLogResponse(

        UUID id,
        UUID LinkId,
        LocalDateTime accessTime
) {
}
