package com.github.codeine200.soltracker.config;

import com.zaxxer.hikari.HikariDataSource;
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
        HikariDataSource ds = clickhouseProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.setIdleTimeout(30000);
        ds.setMaxLifetime(1800000);
        ds.setConnectionTimeout(30000);

        return ds;
    }

    @Bean
    public JdbcTemplate clickhouseJdbcTemplate(
            @Qualifier("clickhouseDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

}