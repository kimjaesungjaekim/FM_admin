package com.developer.fillme.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:MM:SS"
    )
    private LocalDateTime timestamp = LocalDateTime.now();
    private String code;
    private String error;
    private String message;

    public ExceptionResponse(String code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }
}
