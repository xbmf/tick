package com.solactive.tick.api.controller;


import com.solactive.tick.api.domain.TickInput;
import com.solactive.tick.api.broker.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class IndexController {

    @Autowired
    private Producer producer;

    @PostMapping("/ticks")
    public ResponseEntity ticks(@Valid @RequestBody TickInput tickInput) {
        producer.publishTick(tickInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
