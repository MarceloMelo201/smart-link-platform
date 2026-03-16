package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.entity.Link;
import com.mm.smart_link_platform.service.LinkService;
import com.mm.smart_link_platform.service.RedirectService;
import org.springframework.stereotype.Service;

@Service
public class RedirectServiceImpl implements RedirectService {

    private final LinkService linkService;

    public RedirectServiceImpl(LinkService linkService) {
        this.linkService = linkService;
    }


    @Override
    public String resolveLink(String shortCode) {
        Link link = linkService.findByShortCode(shortCode);
        return link.getOriginalUrl();
    }
}
