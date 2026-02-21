package org.tutortoise.service.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class HttpRestResponse {

    private HttpStatus status;
    private String operationStatus;
    private String message;

}
