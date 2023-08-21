package com.hanium.diARy.diary.service;

import org.springframework.stereotype.Service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Service
public class ClovaService {

    public String performSentimentAnalysis(String Content) {
        String clovaUrl = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-APIGW-API-KEY-ID", "42t0ckmx5q");
        headers.set("X-NCP-APIGW-API-KEY", "2yMMWT2oUNQMJOV7yiJeb0WmKK3RcH7AYXYPM1Ft");

        String requestBody = "{\"content\":\"" + Content + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                clovaUrl, HttpMethod.POST, requestEntity, String.class
        );
        String response = responseEntity.getBody();

        return response;
    }

}
