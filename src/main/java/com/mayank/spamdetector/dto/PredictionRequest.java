package com.mayank.spamdetector.dto;

public class PredictionRequest {

    private String message;

    public PredictionRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}