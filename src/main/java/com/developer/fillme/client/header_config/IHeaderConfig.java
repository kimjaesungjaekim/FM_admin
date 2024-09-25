package com.developer.fillme.client.header_config;

import org.springframework.http.HttpEntity;

public interface IHeaderConfig {
    HttpEntity<Object> headerConfig(String accessToken);
}
