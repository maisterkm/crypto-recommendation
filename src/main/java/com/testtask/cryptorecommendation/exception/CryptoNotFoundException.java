package com.testtask.cryptorecommendation.exception;

public class CryptoNotFoundException extends RuntimeException{
    public CryptoNotFoundException(String message) {
        super(message);
    }
}
