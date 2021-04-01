package com.solactive.tick.api.broker.consumer;

import com.solactive.tick.api.domain.IndexStatsMessage;
import com.solactive.tick.api.domain.MessageEvent;
import com.solactive.tick.api.serializer.ObjectSerializer;
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

    @Override
    @KafkaListener(topics = "${api.broker.consumer.statsTopic}", groupId = "${api.broker.groupId}")
    public void receiveIndexStats(String message) {
        var indexStatsMessage = serializer.toObject(IndexStatsMessage.class, message);
        log.info("new index stats message for api: {}", indexStatsMessage);
        eventBus.publishEvent(new MessageEvent<>(indexStatsMessage));
    }
}
