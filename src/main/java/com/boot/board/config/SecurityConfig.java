package com.boot.board.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.boot.board.service.BoardService;
import com.boot.board.security.CustomAccessDeniedHandler;
import com.boot.board.security.CustomAuthenticationFailureHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) 
public class SecurityConfig  {

	@Autowired BoardService boardservice;
	
	@Autowired private  UserDetailsService userDetailsService;
	
    @Autowired private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
	        .authorizeRequests()
		        .antMatchers("/", "/index.jsp", "/signUpPro", "/signUp","/css/**","/js/**","/static/**","/resources",
		        		"/actuator/prometheus" ).permitAll() // 인증 없이 접근 허용
		        .anyRequest().authenticated()
		        .and()
	        .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler()) // 예외처리 
                .and()
            .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/signIn")
                .defaultSuccessUrl("/board/search", true)  // 로그인 성공 시 리다이렉트할 URL 설정
                .permitAll()
                .failureHandler(customAuthenticationFailureHandler)
                .and()
            .logout()
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/") // 로그아웃 성공 후 리디렉션할 URL
                .permitAll()   //로그아웃 기능 활성화 
                .invalidateHttpSession(true) //세션무효화
                .deleteCookies("JSESSIONID") // 세션 쿠기 삭제 
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .csrf() .disable();
        return http.build();
    }
    
    
    //security 기본설정
    public void SecurityFilterChain(AuthenticationManagerBuilder auth) throws Exception {  // 다시확인해보기 
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}