package ru.netology.moneytransferservice.rest.advice;

import java.util.concurrent.atomic.AtomicInteger;
import org.openapitools.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {
  private final static AtomicInteger count = new AtomicInteger();

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setId(count.incrementAndGet());
    errorDto.setMessage(e.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleIException(Exception e) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.setId(count.incrementAndGet());
    errorDto.setMessage(e.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_GATEWAY);
  }
}
