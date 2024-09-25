package com.developer.fillme.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleSecurityException(Exception exception) {

        if (exception instanceof BadCredentialsException) {
            return handlerJWResponse(HttpStatus.UNAUTHORIZED.value(), "The username or password is incorrect", exception.getMessage());
        }

        if (exception instanceof AccountStatusException) {
            return handlerJWResponse(HttpStatus.FORBIDDEN.value(), "The account is locked", exception.getMessage());
        }

        if (exception instanceof AccessDeniedException) {
            return handlerJWResponse(HttpStatus.FORBIDDEN.value(), "You are not authorized to access this resource", exception.getMessage());
        }

        if (exception instanceof SignatureException) {
            return handlerJWResponse(HttpStatus.FORBIDDEN.value(), "The JWT signature is invalid", exception.getMessage());
        }

        if (exception instanceof ExpiredJwtException) {
            return handlerJWResponse(HttpStatus.FORBIDDEN.value(), "The JWT token has expired", exception.getMessage());
        }

        if (exception instanceof MalformedJwtException) {
            return handlerJWResponse(HttpStatus.FORBIDDEN.value(), "Invalid compact JWT string", exception.getMessage());
        }
        exception.printStackTrace(System.err);
        return handlerBaseResponse("APP00", "Unknown error", exception.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidExceptionResponse> handlerResponse(MethodArgumentNotValidException exception) {
        String message = "Invalid field";
        Map<String, Object> errors = new LinkedHashMap<>();
        Stream<ObjectError> exceptions = exception.getBindingResult().getAllErrors().stream();
        exceptions.forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        });
        return handlerValidResponse(HttpStatus.BAD_REQUEST.value(), message, errors);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handlerResponse(BaseException exception) {
        return handlerBaseResponse(exception.getCode(), "Internal Server Error", exception.getMessage());
    }

    private ResponseEntity<ValidExceptionResponse> handlerValidResponse(int code, String detail, Map<String, Object> description) {
        LOGGER.error("[ EXCEPTION-VALID ] - {} ::: {}", detail, description);
        ValidExceptionResponse response = new ValidExceptionResponse(code, detail, description);
        return ResponseEntity.status(code).body(response);
    }

    private ResponseEntity<ExceptionResponse> handlerBaseResponse(String code, String detail, String description) {
        LOGGER.error("[ EXCEPTION ] - {} ::: {}", detail, description);
        ExceptionResponse response = new ExceptionResponse(code, detail, description);
        return ResponseEntity.badRequest().body(response);
    }

    private ResponseEntity<ExceptionResponse> handlerJWResponse(int code, String detail, String description) {
        LOGGER.error("[ EXCEPTION-JWT ] - {} ::: {}", detail, description);
        ExceptionResponse response = new ExceptionResponse("JWT" + code, detail, description);
        return ResponseEntity.status(code).body(response);
    }
}
