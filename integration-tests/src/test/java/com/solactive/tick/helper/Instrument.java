package com.solactive.tick.helper;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.Set;

public class Instrument {
    private static final int INSTRUMENT_LENGTH = 3;
    public static Set<String> random(int size) {
        var randomInstruments = new HashSet<String>();
        while (randomInstruments.size() != size) {
            randomInstruments.add(RandomStringUtils.randomAlphabetic(INSTRUMENT_LENGTH).toUpperCase());
        }
        return randomInstruments;
    }
}
