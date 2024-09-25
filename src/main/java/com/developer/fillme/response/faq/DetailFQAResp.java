package com.developer.fillme.response.faq;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetailFQAResp {
    private Long id;
    private String title;
    private String faqType;
    private String question;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
