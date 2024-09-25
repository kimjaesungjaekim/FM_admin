package com.developer.fillme.request.faq;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListFQAReq {
    @Schema(description = "Type of FAQ: ADVISE, SUBSCRIBE, ETC")
    @Pattern(regexp = "ADVISE|SUBSCRIBE|ECT", message = "Type must be , ADVISE, SUBSCRIBE, ECT")
    private String type;

    @Schema(description = "Page number", example = "1")
    private int page;

    @Schema(description = "Number of items per page", example = "10")
    private int limit;


    public long getPage() {
        if (page < 1) {
            return 1;
        }
        return page;
    }

    public long getLimit() {
        if (limit < 1) {
            return 10;
        }
        return limit;
    }
}
