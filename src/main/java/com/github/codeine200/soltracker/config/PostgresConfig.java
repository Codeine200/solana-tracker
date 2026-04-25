package com.github.codeine200.soltracker.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.github.codeine200.soltracker.repository",
        entityManagerFactoryRef = "postgresEmf",
        transactionManagerRef = "postgresTx"
)
public class PostgresConfig {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean postgresEmf(
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(postgresDataSource())
                .packages("com.github.codeine200.soltracker.entity")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager postgresTx(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
