package com.developer.fillme.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenerateTokenInfo {
    private String username;
    private String email;
}
