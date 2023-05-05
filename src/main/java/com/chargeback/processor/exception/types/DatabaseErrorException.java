package com.chargeback.processor.exception.types;

public class DatabaseErrorException extends RuntimeException {

  private static final long serialVersionUID = 3L;

  public DatabaseErrorException(String msg) {
    super(msg);
  }
}