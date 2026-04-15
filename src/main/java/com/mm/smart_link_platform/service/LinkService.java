package com.mm.smart_link_platform.service;

import com.mm.smart_link_platform.dto.CreateLinkRequest;
import com.mm.smart_link_platform.dto.CreateLinkResponse;
import com.mm.smart_link_platform.dto.LinkResponse;
import com.mm.smart_link_platform.entity.Link;

public interface LinkService {

    CreateLinkResponse createShortLink(CreateLinkRequest request);
    LinkResponse findActiveLinkByShortCode(String shortCode);
    LinkResponse findByShortCode(String shortCode);

}
