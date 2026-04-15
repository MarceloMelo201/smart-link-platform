package com.mm.smart_link_platform.service;

import com.mm.smart_link_platform.dto.ClickEvent;
import com.mm.smart_link_platform.dto.LinkStatsResponse;

public interface AnalyticsService {

    void registerAccess(ClickEvent clickEvent);
    LinkStatsResponse getLinkStats(String shortCode);
}
