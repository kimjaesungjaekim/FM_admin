package com.developer.fillme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInfo {
    private long limit;
    private long offset;
    private long totalPages;
    private long totalRecords;
}
