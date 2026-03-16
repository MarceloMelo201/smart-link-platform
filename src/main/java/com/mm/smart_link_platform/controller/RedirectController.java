package com.mm.smart_link_platform.controller;

import com.mm.smart_link_platform.service.RedirectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class RedirectController {

    private final RedirectService service;

    public RedirectController(RedirectService service) {
        this.service = service;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> resolveLink(@PathVariable String shortCode) {
        String url = service.resolveLink(shortCode);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
