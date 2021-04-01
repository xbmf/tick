package com.solactive.tick.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private Instant date;
    private ErrorCode code;
    private String message;
}
