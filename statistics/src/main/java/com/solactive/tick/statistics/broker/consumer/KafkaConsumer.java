package com.solactive.tick.statistics.broker.consumer;

import com.solactive.tick.statistics.domain.MessageEvent;
import com.solactive.tick.statistics.domain.TickMessage;
import com.solactive.tick.statistics.serializer.ObjectSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer implements Consumer {

    @Autowired
    ApplicationEventPublisher eventBus;

    @Autowired
    ObjectSerializer serializer;


    @KafkaListener(topics = "${statistics.broker.consumer.tickTopic}",
            groupId = "${statistics.broker.groupId}")
    public void receiveTick(String message) {
        var tickMessage = serializer.toObject(TickMessage.class, message);
        log.info("new tick message for statistics service: {}", message);
        eventBus.publishEvent(new MessageEvent<>(tickMessage));
    }
}
