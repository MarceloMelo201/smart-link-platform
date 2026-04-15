package com.mm.smart_link_platform.controller;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.dto.LinkStatsResponse;
import com.mm.smart_link_platform.service.AnalyticsService;
import com.mm.smart_link_platform.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Links", description = "Link creation and management operations.")
@RestController
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService linkService;
    private final AnalyticsService analyticsService;

    public LinkController(LinkService linkService, AnalyticsService analyticsService) {
        this.linkService = linkService;
        this.analyticsService = analyticsService;
    }

    @Operation(summary = "Create shortened link.",
            description = "It receives a URL and returns a short code for redirection."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Link created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid URL.")
    })
    @PostMapping
    public ResponseEntity<CreateLinkResponse> createShortLink(
            @RequestBody @Valid CreateLinkRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(linkService.createShortLink(request));
    }

    @Operation(summary = "Get link stats.",
            description = "Returns the status of the specified link."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statistics returned successfully."),
            @ApiResponse(responseCode = "403", description = "Link disabled."),
            @ApiResponse(responseCode = "404", description = "Link not found."),
            @ApiResponse(responseCode = "410", description = "Link expired.")
    })
    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<LinkStatsResponse> getLinkStats(@PathVariable String shortCode) {
        return ResponseEntity.ok(analyticsService.getLinkStats(shortCode));
    }
}
