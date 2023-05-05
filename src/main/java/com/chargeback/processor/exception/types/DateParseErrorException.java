package com.chargeback.processor.exception.types;

public class DateParseErrorException extends RuntimeException {

  private static final long serialVersionUID = 5L;

  public DateParseErrorException(String msg) {
    super(msg);
  }
}