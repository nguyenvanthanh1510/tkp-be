package com.dev.tkpbe.configs.exceptions;


import java.rmi.ServerException;
import java.util.List;

import com.dev.tkpbe.commons.constants.TkpConstant;
import com.dev.tkpbe.commons.enums.ResponseStatus;
import com.dev.tkpbe.models.responses.BaseOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(value = JwtAuthenticationException.class)
  public ResponseEntity<Object> handleJwtAuthenticationException(JwtAuthenticationException e) {
    log.error("ERROR JwtAuthenticationException: {}", e.getMessage(), e);
    return new ResponseEntity<>(
        BaseOutput.builder().errors(List.of(e.getMessage())).status(ResponseStatus.FAILED).build(),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    log.error("ERROR HttpMessageNotReadableException: {}", e.getMessage(), e);
    return new ResponseEntity<>(
        BaseOutput.builder().errors(List.of(e.getMessage())).status(ResponseStatus.FAILED).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    log.error("ERROR MethodArgumentTypeMismatchException: {}", e.getMessage(), e);
    return new ResponseEntity<>(
        BaseOutput.builder().errors(List.of(e.getMessage())).status(ResponseStatus.FAILED).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = AuthenticationException.class)
  public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
    log.error("ERROR AuthenticationException: {}", e.getMessage(), e);
    return new ResponseEntity<>(
        BaseOutput.builder().errors(List.of(TkpConstant.ERROR.AUTH.fAILED)).status(ResponseStatus.FAILED).build(),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = TkpCommonException.class)
  public ResponseEntity<Object> handleDsdFileException(TkpCommonException e) {
    log.error("ERROR DsdCommonException: {}", e.getMessage(), e);
    return new ResponseEntity<>(
        BaseOutput.builder().errors(List.of(e.getMessage())).status(ResponseStatus.FAILED).build(),
        HttpStatus.OK);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Object> handleDsdFileException(Exception e) {
    log.error("ERROR Exception: {}", e.getMessage(), e);
    return new ResponseEntity<>(
        BaseOutput.builder().errors(List.of(TkpConstant.ERROR.SERVER.INTERNAL)).status(ResponseStatus.FAILED).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = ServerException.class)
  public ResponseEntity<Object> handleDsdFileException(ServerException e) {
    log.error("ERROR Exception: {}", e.getMessage(), e);
    return new ResponseEntity<>(
        BaseOutput.builder().errors(List.of(TkpConstant.ERROR.SERVER.INTERNAL)).status(ResponseStatus.FAILED).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
