package com.uade.financialEntity;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {


    private static final String SQL_HOST_KEY = "www.exterionmu.com";
    private static final String SQL_DATABASE_KEY = "exterion_bedecarats";
    private static final String SQL_USER_KEY = "exterion_bedecarats";
    private static final String SQL_PASSWORD_KEY = "JknxjcYsmthszLU9sJ";
    private static final String DEFAULT_SQL_HOST = "localhost:3306";

    DataSourceConfig() {
    }

    @Bean(name = "database")
    public HikariDataSource dataSource() {
        return getProdDataSource();
    }

    private HikariDataSource getProdDataSource() {
        String host = SQL_HOST_KEY;
        String db = SQL_DATABASE_KEY;
        String username = SQL_USER_KEY;
        String password = SQL_PASSWORD_KEY;

        return DataSourceBuilder.create().type(HikariDataSource.class)
                .username(username)
                .password(password)
                .url("jdbc:mysql://" + host + "/" + db + "?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    private HikariDataSource getTestDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .username("sa")
                .password("sa")
                .url("jdbc:h2:mem:testdb")
                .driverClassName("org.h2.Driver")
                .build();
    }

}