package ru.skypro.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class TestConfig {

    @Value("${spring.datasource.H2.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.H2.driver}")
    private String driver;
    @Value("${spring.datasource.H2.username}")
    private String username;
    @Value("${spring.datasource.H2.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url(dataSourceUrl)
                .username(username)
                .password(password)
                .build();
    }
}
