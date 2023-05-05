package com.chargeback.processor.exception;

import com.chargeback.processor.exception.types.DatabaseErrorException;
import com.chargeback.processor.exception.types.DateParseErrorException;
import com.chargeback.processor.exception.types.FileErrorException;
import com.chargeback.processor.exception.types.InternalServerErrorException;
import com.chargeback.processor.exception.types.KeyParameterErrorException;
import com.chargeback.processor.exception.types.UnexpectedFileErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

  @ExceptionHandler(InternalServerErrorException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage internalServerError(InternalServerErrorException ex, WebRequest request) {
    return getStandardErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(FileErrorException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage fileError(FileErrorException ex, WebRequest request) {
    return getStandardErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UnexpectedFileErrorException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage unexpectedFileError(UnexpectedFileErrorException ex, WebRequest request) {
    return getStandardErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DatabaseErrorException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage databaseError(DatabaseErrorException ex, WebRequest request) {
    return getStandardErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DateParseErrorException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage dateParseError(DateParseErrorException ex, WebRequest request) {
    return getStandardErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(KeyParameterErrorException.class)
  @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
  public ErrorMessage parameterError(KeyParameterErrorException ex, WebRequest request) {
    return getStandardErrorMessage(ex, request, HttpStatus.PRECONDITION_FAILED);
  }

  private ErrorMessage getStandardErrorMessage(Exception ex, WebRequest request, HttpStatus httpStatus) {
    log.error("Exception occurred: " + ex.getMessage());
    return ErrorMessage.builder()
        .statusCode(httpStatus.value())
        .timestamp(LocalDateTime.now())
        .message(ex.getMessage())
        .description(request.getDescription(false))
        .build();
  }
}