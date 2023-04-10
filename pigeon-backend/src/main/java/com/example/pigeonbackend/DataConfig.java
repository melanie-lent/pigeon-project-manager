package com.example.pigeonbackend;

import com.example.pigeonbackend.repo.TaskSpecifications;
import com.example.pigeonbackend.service.AuthHelper;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@Configuration
@ComponentScan({"com.example.pigeonbackend"})
@EntityScan(basePackages = "com.example.pigeonbackend")
@EnableJpaRepositories(basePackages = "com.example.pigeonbackend")
public class DataConfig {
    @Bean
    public AuthHelper authHelper() { return new AuthHelper(); }
    @Bean
    public InMemoryHttpExchangeRepository createTraceRepository() {
        return new InMemoryHttpExchangeRepository();
    }
    @Bean
    public InMemoryAuditEventRepository auditEventRepository() {
        return new InMemoryAuditEventRepository();
    }
    @Bean
    public TaskSpecifications taskSpecifications() { return new TaskSpecifications(); }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }


//    @Bean
//    public BCryptPasswordEncoder bcrypt() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    UserDetailsService customUserDetailsService() {
//        return new UserDetailsService();
//    }
//
}
