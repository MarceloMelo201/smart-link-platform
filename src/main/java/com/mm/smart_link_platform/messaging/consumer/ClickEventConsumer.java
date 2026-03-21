package com.mm.smart_link_platform.messaging.consumer;

import com.mm.smart_link_platform.config.RabbitMQConfig;
import com.mm.smart_link_platform.dto.ClickEvent;
import com.mm.smart_link_platform.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickEventConsumer {

    private final AnalyticsService analyticsService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeClickEvent(ClickEvent clickEvent) {
        try {
            log.info("Consuming click event | linkId={}", clickEvent.linkId());
            analyticsService.registerAccess(clickEvent);

        } catch (Exception e) {
            log.error("Error processing click event linkId={}",
                    clickEvent.linkId(), e);
        }

    }
}
