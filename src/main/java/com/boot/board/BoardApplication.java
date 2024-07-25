package com.boot.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.client.RestTemplate;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 기본로그인화면 제거 
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
	
    @Bean
    public RestTemplate restTemplate() {    // 날씨 api 때문에 추가  원래는 Restful http 요청용 
        return new RestTemplate();
    }
    
}
