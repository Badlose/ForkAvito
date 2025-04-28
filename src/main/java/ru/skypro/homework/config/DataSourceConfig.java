package ru.skypro.homework.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

}
