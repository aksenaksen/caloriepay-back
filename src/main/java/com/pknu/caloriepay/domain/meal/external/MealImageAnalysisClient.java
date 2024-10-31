package com.pknu.caloriepay.domain.meal.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pknu.caloriepay.domain.file.dto.response.UploadResult;
import com.pknu.caloriepay.domain.meal.dto.FoodDto;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MealImageAnalysisClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${external.api.image-analysis-url}")
    private String url;



    public List<FoodDto> analyze(UploadResult res) {

        // JSON 형식의 요청 본문을 구성
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("imgUrl", res.imgUrl());

        // JSON 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity로 요청 본문과 헤더를 감싸기
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // 응답에서 "food" 키의 배열을 추출
            List<FoodDto> foodList = Arrays.asList(objectMapper.convertValue(response.getBody().get("food"), FoodDto[].class));
            return foodList;
        } else {
            throw new CustomException(ResCode.INTERNAL_SERVER_ERROR);
        }
    }

}
