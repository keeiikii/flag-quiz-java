package com.example.flagquiz.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotEnvConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        
        try {
            Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMissing()
                .load();
            
            Map<String, Object> envMap = new HashMap<>();
            dotenv.entries().forEach(entry -> {
                envMap.put(entry.getKey(), entry.getValue());
                // システムプロパティとしても設定
                System.setProperty(entry.getKey(), entry.getValue());
            });
            
            MapPropertySource propertySource = new MapPropertySource("dotenv", envMap);
            environment.getPropertySources().addFirst(propertySource);
            
        } catch (Exception e) {
            System.out.println("Could not load .env file: " + e.getMessage());
        }
    }
}