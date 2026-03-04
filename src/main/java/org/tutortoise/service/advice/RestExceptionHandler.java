package org.tutortoise.service.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.tutortoise.service.credit.InsufficientCreditsException;
import org.tutortoise.service.login.InvalidCredentialsException;
import org.tutortoise.service.login.InvalidRoleException;
import org.tutortoise.service.login.UserNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<HttpRestResponse> handleValidationExceptions(
      HandlerMethodValidationException ex) {
    HttpRestResponse response = new HttpRestResponse();
    response.setStatus(HttpStatus.BAD_REQUEST);
    response.setMessage(ex.getAllErrors().getFirst().getDefaultMessage());
    response.setOperationStatus(HttpRestResponse.FAILED);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<HttpRestResponse> handleUserNotFoundException(
      UserNotFoundException ex) {
    HttpRestResponse response = new HttpRestResponse();
    response.setStatus(HttpStatus.UNAUTHORIZED);
    response.setMessage(ex.getMessage());
    response.setOperationStatus(HttpRestResponse.FAILED);
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<HttpRestResponse> handleInvalidCredentialsException(
      InvalidCredentialsException ex) {
    HttpRestResponse response = new HttpRestResponse();
    response.setStatus(HttpStatus.UNAUTHORIZED);
    response.setMessage(ex.getMessage());
    response.setOperationStatus(HttpRestResponse.FAILED);
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InvalidRoleException.class)
  public ResponseEntity<HttpRestResponse> handleInvalidRoleException(
      InvalidRoleException ex) {
    HttpRestResponse response = new HttpRestResponse();
    response.setStatus(HttpStatus.BAD_REQUEST);
    response.setMessage(ex.getMessage());
    response.setOperationStatus(HttpRestResponse.FAILED);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<HttpRestResponse> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    HttpRestResponse response = new HttpRestResponse();
    response.setStatus(HttpStatus.BAD_REQUEST);
    response.setMessage(ex.getMessage());
    response.setOperationStatus(HttpRestResponse.FAILED);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InsufficientCreditsException.class)
  public ResponseEntity<HttpRestResponse> handleInsufficientCreditsException(
      InsufficientCreditsException ex) {
    HttpRestResponse response = new HttpRestResponse();
    response.setStatus(HttpStatus.PAYMENT_REQUIRED);
    response.setMessage(ex.getMessage());
    response.setOperationStatus(HttpRestResponse.FAILED);
    return new ResponseEntity<>(response, HttpStatus.PAYMENT_REQUIRED);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<HttpRestResponse> handleRuntimeException(RuntimeException ex) {
    HttpRestResponse response = new HttpRestResponse();
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    response.setMessage(ex.getMessage());
    response.setOperationStatus(HttpRestResponse.FAILED);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}


