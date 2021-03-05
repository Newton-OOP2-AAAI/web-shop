package org.newton.webshop.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, e));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @ExceptionHandler(CartNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CartNotFoundException e) {

        String error = "Could not retrieve resource";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, e));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CustomerNotFoundException e) {
        String error = "Could not retrieve resource";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, e));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(ItemNotFoundException e) {
        String error = "Could not retrieve resource";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, e));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(ProductNotFoundException e) {
        String error = "Could not retrieve resource";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, e));
    }


    public Exception throwException(Exception e) {
        return throwException(e);
    }
}
