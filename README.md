# Springboot CRUD 게시판 


## 🖥️ 프로젝트 소개
Springboot 게시판입니다 

## 🕰️ 개발 기간
* 24.06.20일 - 24.07.10일 (기본 CRUD 기능 완료)
* 24.07.11일 - 24.07.20일( spring security 로그인추가 )


### ⚙️ 개발 환경
- `Java 8`
- `JDK 1.8.0`
- **IDE** : STS 
- **Database** : Maria DB
- **ORM** : Mybatis
- **Server** : tomcat

## 배포 링크

https://inc-gilda-forstudy-b457f6f4.koyeb.app

http://3.38.245.89:8080/ 

test id/pw : zz/zz  관리자권한 

-**호스팅플랫폼** :  Koyeb ,Amazon Ec2, Amazon RDS


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

<details>
	
  <summary>이미지 보기 </summary>
  <img src="https://github.com/user-attachments/assets/222d332e-bd45-48d6-a23e-f4e0ae178b38" alt="Example Image">
</details>

<details>
	
  <summary>쿼리문 보기 </summary>
  
```
	<select id="searchBoard" resultMap="BoardResultMap">
	    SELECT b_id, b_title, b_content, b_date, b_writer, b_group, b_order, b_depth
	    FROM board
	    <where>
	        <if test="search.keyword != null and search.keyword != ''">
	            <choose>
	                <when test="search.searchType == 'title'">
	                    b_title LIKE CONCAT('%', #{search.keyword}, '%')
	                </when>
	                <when test="search.searchType == 'content'">
	                    b_content LIKE CONCAT('%', #{search.keyword}, '%')
	                </when>
	                <when test="search.searchType == 'writer'">
	                    b_writer LIKE CONCAT('%', #{search.keyword}, '%')
	                </when>
	                <otherwise>
	                    (b_title LIKE CONCAT('%', #{search.keyword}, '%')
	                    OR b_content LIKE CONCAT('%', #{search.keyword}, '%')
	                    OR b_writer LIKE CONCAT('%', #{search.keyword}, '%'))
	                </otherwise>
	            </choose>
	        </if>
	    </where>
	    ORDER BY b_group DESC, b_order ASC, b_depth ASC
	    LIMIT #{limit} OFFSET #{offset}
	</select>
 ```

</details>


## CRUD - 

<details>
	
  <summary>쿼리문 보기 </summary>
  <img src="https://github.com/user-attachments/assets/4d225447-027a-42d4-b9de-b0815f620929" alt="Example Image">
</details>

<details>
	
  <summary>동작 보기 </summary>
  <img src="https://github.com/user-attachments/assets/16efe027-0133-4f12-9309-6181acb37b28" alt="Example Image">
</details>
  
   
</details>

</details>

## 성능모니터링 

  <img src="https://github.com/user-attachments/assets/e8141321-d6ea-4e2d-a63b-1c3d509932f9" alt="Example Image">



## 트러블슈팅 


* Error parsing HTTP request header java.io.EOFException: null   배포후 실행시 해당오류 발생 

 -https://sk5614.tistory.com/44  war 파일로 실행시켜서 해결 

*RDS를 MySQL Workbench로 접속이 안되는 문제 

 -https://sk5614.tistory.com/43 인바운드 규칙설정으로 해결

*EC2 페어키 권한 오류 

 -https://sk5614.tistory.com/42  WSL에서 윈도우와 우분투 권한 충돌 문제로 예상 




<details>
	
  <summary>*Spring security 관련  </summary>
  
   -가장 힘들었던부분 특정 url은 해당권한만 접근가능하게 만드는과정에서 @PreAuthorize을 사용하려고 했습니다.  

  그러나 기존 로그인은 HTTP session 로그인이고  @PreAuthorize는 security 기능을 써야 가능해서 

  기존 로그인도 security 로그인으로 바꾸고 예외처리에서 시간을 많이 보냈습니다. 

  처음 시작할 당시 security 로그인은 controller 들어오기전에 처리된다는걸 모르는 상태에서 무작정 시작해서 시간낭비를 많이 했습니다. 

  구글링으로 다른 사람들 코드 참고하고 디버깅해가면서 필요한 기능만 구현했습니다 

  ![image](https://github.com/user-attachments/assets/f3c4292d-3bf0-47d9-ab31-f5f10cb7e273)

  


  

</details>

  




