package org.tutortoise.service.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class HttpRestResponse {

    public final static String SUCCESS = "Success";
    public final static String FAILED = "Failed";

    private HttpStatus status;
    private String operationStatus;
    private String message;

}