package com.solactive.tick.api.controller;

import com.solactive.tick.api.domain.InstrumentStat;
import com.solactive.tick.api.exception.DataNotAvailableException;
import com.solactive.tick.api.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    public InstrumentStat getAllStats() {
        return statisticsService.getForAll()
                .orElseThrow(DataNotAvailableException::new);
    }

    @GetMapping("/statistics/{instrument}")
    public InstrumentStat getForInstrument(@PathVariable String instrument) {
        return statisticsService.getForInstrument(instrument.toUpperCase())
                .orElseThrow(DataNotAvailableException::new);
    }
}
