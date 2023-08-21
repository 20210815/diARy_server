package com.hanium.diARy.diary.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Service
public class ClovaService {

    public double performSentimentAnalysis(String Content) {
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
        ObjectMapper objectMapper = new ObjectMapper();
        double positive = 0;
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode confidence = jsonNode.get("document").get("confidence");
            //double negative = confidence.get("negative").asDouble();
            positive = confidence.get("positive").asDouble();
            //double neutral = confidence.get("neutral").asDouble();

            // negative, positive, neutral 값을 사용하여 원하는 처리를 수행합니다.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return positive;
    }

}
