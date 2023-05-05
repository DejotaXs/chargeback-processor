package com.chargeback.processor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class PropertiesLoader {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.driverClassName}")
    private String databaseDriver;

    @Value("${spring.datasource.username}")
    private String databaseUser;

    @Value("${spring.datasource.password}")
    private String databasePassword;
}
