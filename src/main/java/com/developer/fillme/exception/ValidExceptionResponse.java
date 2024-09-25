package com.developer.fillme.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ValidExceptionResponse {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:MM:SS"
    )
    private LocalDateTime timestamp = LocalDateTime.now();
    private Integer code;
    private String error;
    private Map<String, Object> message;

    public ValidExceptionResponse(int code, String error, Map<String, Object> message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }
}
