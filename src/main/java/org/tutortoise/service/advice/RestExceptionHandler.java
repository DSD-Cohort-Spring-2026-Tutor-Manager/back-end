package org.tutortoise.service.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<HttpRestResponse> handleValidationExceptions(HandlerMethodValidationException ex) {
        HttpRestResponse response = new HttpRestResponse();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(ex.getAllErrors().getFirst().getDefaultMessage());
        response.setOperationStatus("failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
