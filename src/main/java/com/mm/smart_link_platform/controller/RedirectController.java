package com.mm.smart_link_platform.controller;

import com.mm.smart_link_platform.service.RedirectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "Redirect", description = "Link redirection.")
@RestController
public class RedirectController {

    private final RedirectService redirectService;

    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @Operation(
            summary = "Redirect to original URL.",
            description = "It receives a shortcode and redirects to the original URL. " +
                    "A click event is sent to RabbitMQ for asynchronous processing."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Redirection completed."),
            @ApiResponse(responseCode = "403", description = "Link disabled."),
            @ApiResponse(responseCode = "404", description = "Link not found."),
            @ApiResponse(responseCode = "410", description = "Link expired.")
    })
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> resolveLink(
            @PathVariable String shortCode,
            HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");
        String url = redirectService.resolveLink(shortCode, ip, userAgent, referer);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
