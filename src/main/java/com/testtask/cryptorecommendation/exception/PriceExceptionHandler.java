package com.testtask.cryptorecommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PriceExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResponseExceptionIncorrectCrypto> handleException(CryptoNotFoundException exception) {
        ResponseExceptionIncorrectCrypto response = new ResponseExceptionIncorrectCrypto(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
