package com.mm.smart_link_platform.service;

import com.mm.smart_link_platform.dto.LinkStatsResponse;
import com.mm.smart_link_platform.entity.Link;

public interface AnalyticsService {

    void registerAccess(Link link, String ip, String userAgent, String referer);
    LinkStatsResponse getLinkStats(String shortCode);
}
