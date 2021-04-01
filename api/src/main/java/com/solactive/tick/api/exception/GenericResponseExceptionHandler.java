package com.solactive.tick.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class GenericResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        System.out.println(ex.getMessage());
        ex.printStackTrace();
        logger.error("error on request:", ex);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn("request not valid:", ex);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(DataNotAvailableException.class)
    private ResponseEntity<Object> handleDataNotAvailable(DataNotAvailableException ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
