package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.dto.LinkStatsResponse;
import com.mm.smart_link_platform.entity.AccessLog;
import com.mm.smart_link_platform.entity.Link;
import com.mm.smart_link_platform.repository.AccessRepository;
import com.mm.smart_link_platform.service.AnalyticsService;
import com.mm.smart_link_platform.service.LinkService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AccessRepository accessRepository;
    private final LinkService linkService;

    public AnalyticsServiceImpl(AccessRepository accessRepository, LinkService linkService) {
        this.accessRepository = accessRepository;
        this.linkService = linkService;
    }

    @Override
    public void registerAccess(Link link, String ip, String userAgent, String referer) {
        UUID linkId = link.getLinkId();

        AccessLog access = AccessLog
                .builder()
                .linkId(linkId)
                .ip(ip)
                .userAgent(userAgent)
                .referer(referer)
                .build();

        accessRepository.save(access);
    }

    @Override
    public LinkStatsResponse getLinkStats(String shortCode) {
        Link link = linkService.findByShortCode(shortCode);
        Long totalClicks = accessRepository.countByLinkId(link.getLinkId());
        Long uniqueIps = accessRepository.countUniqueVisitors(link.getLinkId());

        return LinkStatsResponse
                .builder()
                .originalUrl(link.getOriginalUrl())
                .shortUrl(link.getShortCode())
                .totalClicks(totalClicks)
                .uniqueIps(uniqueIps)
                .createdAt(link.getCreatedAt())
                .build();
    }
}
