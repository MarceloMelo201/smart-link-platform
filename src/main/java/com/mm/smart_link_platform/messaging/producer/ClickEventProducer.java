package com.mm.smart_link_platform.messaging.producer;

import com.mm.smart_link_platform.config.RabbitMQConfig;
import com.mm.smart_link_platform.dto.ClickEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClickEventProducer {

    private final AmqpTemplate amqpTemplate;

    public void publishClickEvent(ClickEvent clickEvent) {
        try {
            amqpTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    clickEvent);

            log.info("Event sent successfully. | linkId={} | ip={}",
                    clickEvent.linkId(), clickEvent.ip());
        }
        catch (AmqpException e) {
            log.error("Error sending click event. | linkId={} | ip={}",
                    clickEvent.linkId(), clickEvent.ip(), e);
        }
    }
}
