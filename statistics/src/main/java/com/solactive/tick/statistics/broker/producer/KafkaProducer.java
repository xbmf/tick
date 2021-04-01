package com.solactive.tick.statistics.broker.producer;

import com.solactive.tick.statistics.domain.IndexStatsMessage;
import com.solactive.tick.statistics.serializer.ObjectSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer implements Producer {

    @Value("${statistics.broker.producer.statsTopic}")
    private String statsTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectSerializer objectSerializer;

    @Override
    public void publishStats(IndexStatsMessage indexStatsMessage) {
        var message = objectSerializer.toString(indexStatsMessage);
        log.info("sending message: {}", message);
        kafkaTemplate.send(statsTopic, message);
    }
}
