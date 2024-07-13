package com.boot.board.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boot.board.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Utils utils;

    public Map<String, Object> getCurrentWeather(String lat, String lon) throws IOException {
        String serviceKey = "7c7cfda23137e9e1d136e8ca5d565cc5";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather";

        // URI 생성
        String finalUri = String.format("%s?lat=%s&lon=%s&appid=%s", apiUrl, lat, lon, serviceKey);

        // API 호출 및 응답 받기
        ResponseEntity<String> response = restTemplate.getForEntity(finalUri, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();

            // JSON 데이터를 Map으로 변환하여 반환
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> weatherData = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});

            // 온도 데이터를 섭씨로 변환하여 Map에 추가
            double tempKelvin = ((Number) ((Map<String, Object>) weatherData.get("main")).get("temp")).doubleValue();
            int tempCelsius = utils.convertTemp(tempKelvin);
            weatherData.put("tempCelsius", tempCelsius);
            
//            // 최고 온도
//            double tempMaxKelvin = ((Number) ((Map<String, Object>) weatherData.get("main")).get("temp_max")).doubleValue();
//            int tempMaxCelsius = utils.convertTemp(tempMaxKelvin);
//            weatherData.put("tempMaxCelsius", tempMaxCelsius);
//
//            // 최저 온도
//            double tempMinKelvin = ((Number) ((Map<String, Object>) weatherData.get("main")).get("temp_min")).doubleValue();
//            int tempMinCelsius = utils.convertTemp(tempMinKelvin);
//            weatherData.put("tempMinCelsius", tempMinCelsius);
//
//            // 체감 온도
//            double feelsLikeKelvin = ((Number) ((Map<String, Object>) weatherData.get("main")).get("feels_like")).doubleValue();
//            int feelsLikeCelsius = utils.convertTemp(feelsLikeKelvin);
//            weatherData.put("feelsLikeCelsius", feelsLikeCelsius);

            
            return weatherData;
        } else {
            throw new IOException("Failed to retrieve weather data from API");
        }
    }
}
