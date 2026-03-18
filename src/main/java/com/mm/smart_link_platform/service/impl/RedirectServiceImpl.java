package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.entity.Link;
import com.mm.smart_link_platform.service.AnalyticsService;
import com.mm.smart_link_platform.service.LinkService;
import com.mm.smart_link_platform.service.RedirectService;
import org.springframework.stereotype.Service;

@Service
public class RedirectServiceImpl implements RedirectService {

    private final LinkService linkService;
    private final AnalyticsService analyticsService;

    public RedirectServiceImpl(LinkService linkService, AnalyticsService analyticsService) {
        this.linkService = linkService;
        this.analyticsService = analyticsService;
    }

    @Override
    public String resolveLink(String shortCode, String ip, String userAgent, String referer) {
        Link link = linkService.findByShortCode(shortCode);

        try {
            analyticsService.registerAccess(link, ip, userAgent, referer);
        } catch (Exception ignored) {
            //tratar posteriomente com a implementação da fila
        }

        return link.getOriginalUrl();
    }
}
