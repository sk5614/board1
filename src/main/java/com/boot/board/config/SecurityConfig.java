package com.boot.board.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.boot.board.service.BoardService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) 
public class SecurityConfig  {

	@Autowired BoardService boardservice;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
            .csrf().disable() 
            .authorizeRequests()
                .antMatchers("/board/").authenticated() 
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/board/search", true)  // 로그인 성공 시 리다이렉트할 URL 설정
                .permitAll()
                .and()
            .logout()
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/") // 로그아웃 성공 후 리디렉션할 URL
                .permitAll()   //로그아웃 기능 활성화 
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);// 세션 항상 생성
        return http.build();
    }
    
}