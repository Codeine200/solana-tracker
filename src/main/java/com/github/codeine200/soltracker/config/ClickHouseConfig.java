package com.github.codeine200.soltracker.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ClickHouseConfig {

    @Bean
    @ConfigurationProperties("app.datasource.clickhouse")
    public DataSource clickhouseDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public JdbcTemplate clickhouseJdbc(DataSource clickhouseDataSource) {
        return new JdbcTemplate(clickhouseDataSource);
    }
}