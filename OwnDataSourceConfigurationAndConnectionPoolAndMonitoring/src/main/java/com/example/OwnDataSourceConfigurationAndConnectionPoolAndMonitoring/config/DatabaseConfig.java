package com.example.OwnDataSourceConfigurationAndConnectionPoolAndMonitoring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://localhost:3306/main_db");
        config.setUsername("root");
        config.setPassword("P@ssword");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(20000);
        config.setIdleTimeout(600000);

        return new HikariDataSource(config);
    }
}
