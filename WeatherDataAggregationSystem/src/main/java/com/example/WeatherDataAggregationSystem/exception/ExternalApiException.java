package com.example.WeatherDataAggregationSystem.exception;

public class ExternalApiException extends RuntimeException {
  public ExternalApiException(String message) {
    super(message);
  }
}
