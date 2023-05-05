package com.chargeback.processor.exception.types;

public class FileErrorException extends RuntimeException {

  private static final long serialVersionUID = 2L;

  public FileErrorException(String msg) {
    super(msg);
  }
}