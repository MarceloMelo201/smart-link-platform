package com.mm.smart_link_platform.service;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.dto.LinkResponse;

public interface LinkService {

    CreateLinkResponse createShortLink(CreateLinkRequest request);
    LinkResponse findByShortCode(String shortCode);
}
