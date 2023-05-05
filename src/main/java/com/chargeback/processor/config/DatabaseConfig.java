package com.chargeback.processor.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@AllArgsConstructor
public class DatabaseConfig {

    private final PropertiesLoader props;

    @Bean
    public Statement getStatement() throws ClassNotFoundException, SQLException {
        Class.forName(props.getDatabaseDriver());

        return DriverManager.getConnection(
                props.getDatabaseUrl(),
                props.getDatabaseUser(),
                props.getDatabasePassword()
        ).createStatement();
    }
}
