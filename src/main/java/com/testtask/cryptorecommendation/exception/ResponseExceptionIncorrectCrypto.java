package com.testtask.cryptorecommendation.exception;

public class ResponseExceptionIncorrectCrypto {
    private String info;
    private Integer statusCode;
    private Long timestamp;

    public ResponseExceptionIncorrectCrypto(String info, Integer statusCode, Long timestamp) {
        this.info = info;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
