package com.example.pigeonbackend;


import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

public abstract class WebSecurityConfigurerAdapter implements
        WebSecurityConfigurer<WebSecurity> {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()  // (1)
                .and()
                .formLogin().and()   // (2)
                .httpBasic();  // (3)
    }
}

