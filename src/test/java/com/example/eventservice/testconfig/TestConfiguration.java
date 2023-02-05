package com.example.eventservice.testconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type TestConfig.
 * <p>
 * Includes the configuration for integration tests.
 */
@Configuration
@PropertySource("classpath:/application-test.properties")
public class TestConfiguration {
    private static final String TEST_SCHEMA = "classpath:/testdb/testdbschema.sql";
    private static final String TEST_DATA = "classpath:/testdb/testdbdata.sql";

    /**
     * Returns a dataSource bean.
     *
     * @return the DataSource bean
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript(TEST_SCHEMA)
                .addScript(TEST_DATA)
                .build();
    }

    /**
     * Returns a jdbcTemplate bean.
     *
     * @return the JdbcTemplate bean
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}