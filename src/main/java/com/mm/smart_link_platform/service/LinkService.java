package com.mm.smart_link_platform.service;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.entity.Link;

import java.util.UUID;

public interface LinkService {

    CreateLinkResponse createShortLink(CreateLinkRequest request);
    Link findByShortCode(String shortCode);
    void incrementAccessCount(UUID LinkId);
}
