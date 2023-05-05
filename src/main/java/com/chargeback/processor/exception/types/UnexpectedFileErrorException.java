package com.chargeback.processor.exception.types;

public class UnexpectedFileErrorException extends RuntimeException {

  private static final long serialVersionUID = 4L;

  public UnexpectedFileErrorException(String msg) {
    super(msg);
  }
}