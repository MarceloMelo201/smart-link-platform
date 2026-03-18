package com.mm.smart_link_platform.controller;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.dto.LinkStatsResponse;
import com.mm.smart_link_platform.service.AnalyticsService;
import com.mm.smart_link_platform.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService linkService;
    private final AnalyticsService analyticsService;

    public LinkController(LinkService linkService, AnalyticsService analyticsService) {
        this.linkService = linkService;
        this.analyticsService = analyticsService;
    }

    @PostMapping
    public ResponseEntity<CreateLinkResponse> createShortLink(
            @RequestBody @Valid CreateLinkRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(linkService.createShortLink(request));
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<LinkStatsResponse> getLinkStats(@PathVariable String shortCode) {
        return ResponseEntity.ok(analyticsService.getLinkStats(shortCode));
    }

}
