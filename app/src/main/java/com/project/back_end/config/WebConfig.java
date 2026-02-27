package com.project.back_end.config;


import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
public void addCorsMappings(CorsRegistry registry) {        
                .allowedOrigins("*")  // Add your frontend URL here
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specify allowed methods
                .allowedHeaders("*");  // You can restrict headers if needed
    }
}
