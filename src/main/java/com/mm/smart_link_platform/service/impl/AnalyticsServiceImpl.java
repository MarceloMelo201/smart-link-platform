package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.dto.AccessLogResponse;
import com.mm.smart_link_platform.dto.ClickEvent;
import com.mm.smart_link_platform.dto.LinkResponse;
import com.mm.smart_link_platform.dto.LinkStatsResponse;
import com.mm.smart_link_platform.entity.AccessLog;
import com.mm.smart_link_platform.repository.AccessRepository;
import com.mm.smart_link_platform.service.AnalyticsService;
import com.mm.smart_link_platform.service.LinkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AccessRepository accessRepository;
    private final LinkService linkService;

    public AnalyticsServiceImpl(AccessRepository accessRepository, LinkService linkService) {
        this.accessRepository = accessRepository;
        this.linkService = linkService;
    }

    @Override
    public void registerAccess(ClickEvent clickEvent) {
        AccessLog access = AccessLog
                .builder()
                .linkId(clickEvent.linkId())
                .ip(clickEvent.ip())
                .userAgent(clickEvent.userAgent())
                .referer(clickEvent.referer())
                .build();

        accessRepository.save(access);
    }

    @Override
    public LinkStatsResponse getLinkStats(String shortCode) {
        LinkResponse link = linkService.findByShortCode(shortCode);
        Long totalClicks = accessRepository.countByLinkId(link.linkId());
        Long uniqueIps = accessRepository.countUniqueVisitors(link.linkId());
        List<AccessLogResponse> accessLogResponses = accessRepository
                .findTop10ByLinkIdOrderByAccessTimeDesc(link.linkId())
                        .stream()
                        .map(this::toAccessResponse).toList();

        return LinkStatsResponse
                .builder()
                .originalUrl(link.originalUrl())
                .shortCode(link.shortCode())
                .totalClicks(totalClicks)
                .uniqueIps(uniqueIps)
                .createdAt(link.createdAt())
                .recentAccesses(accessLogResponses)
                .build();
    }

    private AccessLogResponse toAccessResponse(AccessLog accessLog) {
        return AccessLogResponse
                .builder()
                .id(accessLog.getId())
                .LinkId(accessLog.getLinkId())
                .accessTime(accessLog.getAccessTime())
                .build();
    }
}
