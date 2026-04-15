package com.mm.smart_link_platform.service.impl;

import com.mm.smart_link_platform.dto.ClickEvent;
import com.mm.smart_link_platform.dto.LinkResponse;
import com.mm.smart_link_platform.messaging.producer.ClickEventProducer;
import com.mm.smart_link_platform.service.LinkService;
import com.mm.smart_link_platform.service.RedirectService;
import org.springframework.stereotype.Service;

@Service
public class RedirectServiceImpl implements RedirectService {

    private final LinkService linkService;
    private final ClickEventProducer producer;

    public RedirectServiceImpl(LinkService linkService, ClickEventProducer producer) {
        this.linkService = linkService;
        this.producer = producer;
    }

    @Override
    public String resolveLink(String shortCode, String ip, String userAgent, String referer) {
        LinkResponse link = linkService.findActiveLinkByShortCode(shortCode);
        ClickEvent clickEvent = ClickEvent
                .builder()
                .linkId(link.linkId())
                .ip(ip)
                .userAgent(userAgent)
                .referer(referer)
                .build();

        producer.publishClickEvent(clickEvent);
        return link.originalUrl();
    }
}
