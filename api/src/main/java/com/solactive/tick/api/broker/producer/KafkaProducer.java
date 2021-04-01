package com.solactive.tick.api.broker.producer;

import com.solactive.tick.api.domain.TickInput;
import com.solactive.tick.api.domain.TickMessage;
import com.solactive.tick.api.serializer.ObjectSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer implements Producer {

    @Value("${api.broker.producer.tickTopic}")
    private String tickTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectSerializer objectSerializer;

    @Override
    public void publishTick(TickInput tickInput) {
        var message = objectSerializer.toString(TickMessage.fromTickInput(tickInput));
        log.debug("Sending message: {}", message);
        kafkaTemplate.send(tickTopic, message);
    }
}
