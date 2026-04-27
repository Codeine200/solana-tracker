package com.github.codeine200.soltracker.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ClickHouseConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.clickhouse")
    public DataSourceProperties clickhouseProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource clickhouseDataSource() {
        return clickhouseProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate clickhouseJdbcTemplate(
            @Qualifier("clickhouseDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

}