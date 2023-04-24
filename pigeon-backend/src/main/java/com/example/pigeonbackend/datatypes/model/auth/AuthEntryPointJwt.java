package com.example.pigeonbackend.datatypes.model.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointJwt extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        int statusCode;
        String errorType;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Context: " + SecurityContextHolder.getContext());
        if (authentication != null) {
            logger.error("Forbidden error: " + authException.toString());
            logger.debug("Authorization header: " + request.getHeaders("Authorization").toString());
            statusCode = HttpServletResponse.SC_FORBIDDEN;
            errorType = "Forbidden";
        } else {
            logger.error("Unauthorized error: " + authException.toString());
            logger.debug("Authorization header: " + request.getHeaders("Authorization").toString());
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            errorType = "Unauthorized";
        }
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = Map.of("status", statusCode, "error", errorType,
                "message", authException.getMessage(), "path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}