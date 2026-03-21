package com.mm.smart_link_platform.infra;

import com.mm.smart_link_platform.exception.InvalidUrlException;
import com.mm.smart_link_platform.exception.LinkExpiredException;
import com.mm.smart_link_platform.exception.LinkInactiveException;
import com.mm.smart_link_platform.exception.UrlNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<RestApiError> handleNotFound(UrlNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<RestApiError> handleInvalidUrl(InvalidUrlException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(LinkExpiredException.class)
    public ResponseEntity<RestApiError> handleExpiredLink(LinkExpiredException ex) {
        return build(HttpStatus.GONE, ex.getMessage());
    }

    @ExceptionHandler(LinkInactiveException.class)
    public ResponseEntity<RestApiError> handleInactiveLink(LinkInactiveException ex) {
        return build(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApiError> handleGeneric(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.");
    }

    private ResponseEntity<RestApiError> build(HttpStatus status, String message) {
        RestApiError error = RestApiError.builder()
                .code(status.value())
                .status(status)
                .timeStamp(LocalDateTime.now())
                .errors(List.of(message))
                .build();

        return ResponseEntity.status(status).body(error);
    }
}