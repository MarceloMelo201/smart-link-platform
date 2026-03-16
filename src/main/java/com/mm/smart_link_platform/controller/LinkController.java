package com.mm.smart_link_platform.controller;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService service;

    @Autowired
    public LinkController(LinkService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<CreateLinkResponse> createShortLink(
            @RequestBody @Valid CreateLinkRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createShortLink(request));
    }

}
