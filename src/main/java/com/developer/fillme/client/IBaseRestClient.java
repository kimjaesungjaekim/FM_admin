package com.developer.fillme.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.Map;

public interface IBaseRestClient {
    <T> T callAPI(String url, HttpMethod method, HttpEntity<Object> request, Class<T> classType);

    <T> T callAPI(String url, HttpMethod method, HttpEntity<Object> request, Class<T> classType, Map<String, Object> variables);
}
