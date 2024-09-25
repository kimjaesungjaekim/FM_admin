package com.developer.fillme.client.impl;

import com.developer.fillme.client.IBaseRestClient;
import com.developer.fillme.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseRestClient implements IBaseRestClient {
    private final RestTemplate client;
    private final ObjectMapper mapper;

    @Override
    public <T> T callAPI(String url, HttpMethod method, HttpEntity<Object> request, Class<T> classType) {
        return callAPI(url, method, request, classType, null);
    }

    @Override
    public <T> T callAPI(String url, HttpMethod method, HttpEntity<Object> request, Class<T> classType, Map<String, Object> variables) {
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<T> response;

        try {
            if (variables != null && !variables.isEmpty()) {
                log.info("[ REQUEST ] ::: [ {} ] [ URL ] ::: {} [ VARIABLE-DATA ] -> {}", method.name(), uri.toUriString(), variables);
                response = client.exchange(uri.toUriString(), method, request, classType, variables);
            } else {
                log.info("[ REQUEST ] ::: [ {} ] [ URL ] ::: {} [ REQUEST-DATA ] -> {}", method.name(), uri.toUriString(), request);
                response = client.exchange(uri.toUriString(), method, request, classType);
            }
            log.info("[ RESPONSE ] ::: [ DATA-BODY ] -> {}", response);
        } catch (HttpClientErrorException exception) {
            log.error("[ ERROR ] ::: [ STATUS CODE ] -> {} [ RESPONSE BODY ] -> {}", exception.getStatusCode(), exception.getResponseBodyAsString());
            throw new BaseException("400", exception.getResponseBodyAsString());
        }
        return response.getBody();
    }

//    private <T> errorResponse(HttpClientErrorException exception) {
//        <T> errorResponse = new BaseResponse<>();
//        try {
//            JsonNode rootNode = mapper.readTree(exception.getResponseBodyAsString());
//            String clientMessageId = rootNode.path("clientMessageId").asText();
//            String errorCode = rootNode.path("errorCode").asText();
//            JsonNode errorDescNode = rootNode.path("errorDesc");
//
//            List<String> errorCodes = new ArrayList<>();
//            if (errorDescNode.isArray()) {
//                errorDescNode.forEach(desc -> errorCodes.add(desc.asText()));
//            }
//            errorResponse.setClientMessageId(clientMessageId);
//            errorResponse.setErrorCode(errorCode);
//            errorResponse.setErrorDesc(errorCodes);
//            log.info("[ RESPONSE ERROR ] ::: [ DATA-BODY ] -> {}", mapper.writeValueAsString(errorResponse));
//        } catch (JsonProcessingException e) {
//            log.error("[ ERROR ] ::: [ MESSAGE ] -> {}", e.getOriginalMessage());
//            throw new RuntimeException(e);
//        }
//        return errorResponse;
//    }
}
