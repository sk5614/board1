# Springboot CRUD 게시판 


## 🖥️ 프로젝트 소개
Springboot 게시판입니다 

## 🕰️ 개발 기간
* 24.06.20일 - 22.07.10일


### ⚙️ 개발 환경
- `Java 8`
- `JDK 1.8.0`
- **IDE** : STS 
- **Database** : Maria DB
- **ORM** : Mybatis
- **Server** : tomcat

## 배포 링크
http://noble-minta-sw5614-3279dd38.koyeb.app/


## 작동 영상 
https://youtu.be/WNnXVCBOxIA


## ERD 
![image](https://github.com/sk5614/board1/assets/169679888/29b58296-a4b7-418f-993f-484ef5e34155)

## 작동 흐름
![image](https://github.com/user-attachments/assets/d529b604-742b-4e59-af35-f827c766eb7e)

## 로그인 화면
<details>
	
  <summary>이미지 보기 </summary>
  <img src="https://github.com/sk5614/board1/assets/169679888/6eee44f5-cbf6-4e9a-8ceb-17d1443d5e1e" alt="Example Image">
</details>


## 게시판 리스트 
<details>
	
  <summary>이미지 보기 </summary>
  <img src="https://github.com/sk5614/board1/assets/169679888/1bfbed4a-5601-46bc-b17b-e2b90ee350ae" alt="Example Image">
</details>



## 날씨 

<details>
	
  <summary>이미지 보기 </summary>
  <img src="https://github.com/user-attachments/assets/56a011f5-e83a-41d9-b6d6-3a09e904181e" alt="Example Image">
</details>
<details>
	
  <summary>코드 보기 </summary>

```
    public Map<String, Object> getCurrentWeather(String lat, String lon) throws IOException {
        String serviceKey = "****************";
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
```

</details>


## 검색


 
## 권한 설정 



#### 답글 - 


<details>
	
  
   
</details>

</details>


#### 개선점 



