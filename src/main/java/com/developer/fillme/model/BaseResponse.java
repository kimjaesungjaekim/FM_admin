package com.developer.fillme.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
    private int code = 200;
    private String message;
    private T results;

    public BaseResponse(T results) {
        this.results = results;
        this.message = "Success";
    }

    public BaseResponse(T results, String message) {
        this.results = results;
        this.message = message;
    }
}
