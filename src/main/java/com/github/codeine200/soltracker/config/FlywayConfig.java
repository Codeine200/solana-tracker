package com.github.codeine200.soltracker.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flywayPostgres(DataSource postgresDataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(postgresDataSource)
                .locations("classpath:db/postgres/migration")
                .load();

        flyway.migrate();
        return flyway;
    }

    @Bean
    public Flyway flywayClickHouse(DataSource clickhouseDataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(clickhouseDataSource)
                .locations("classpath:db/clickhouse/migration")
                .load();

        flyway.migrate();
        return flyway;
    }
}