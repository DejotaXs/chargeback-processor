package com.chargeback.processor.exception.types;

public class KeyParameterErrorException extends RuntimeException {

  private static final long serialVersionUID = 6L;

  public KeyParameterErrorException(String msg) {
    super(msg);
  }
}