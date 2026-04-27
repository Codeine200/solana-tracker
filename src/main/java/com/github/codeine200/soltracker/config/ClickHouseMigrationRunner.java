package com.github.codeine200.soltracker.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class ClickHouseMigrationRunner {

    private final DataSource dataSource;

    public ClickHouseMigrationRunner(@Qualifier("clickhouseDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void run() throws Exception {
        execute("""
            CREATE TABLE IF NOT EXISTS wallets
            (
                wallet String,
                prefix FixedString(3),
                suffix FixedString(3)
            )
            ENGINE = MergeTree
            ORDER BY (prefix, suffix);
                    """);
    }

    private void execute(String sql) throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
        }
    }
}
