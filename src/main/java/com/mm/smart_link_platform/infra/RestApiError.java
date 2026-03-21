package com.mm.smart_link_platform.infra;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record RestApiError(

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timeStamp,

        Integer code,

        HttpStatus status,

        List<String> errors
) {
}
