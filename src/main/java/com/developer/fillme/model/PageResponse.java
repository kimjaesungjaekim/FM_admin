package com.developer.fillme.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T> {
    private int code = 200;
    private String message = "Success";
    private T results;
    private PaginationInfo pagination;

    public PageResponse(T results, PaginationInfo pagination) {
        this.results = results;
        this.pagination = pagination;
    }
}
